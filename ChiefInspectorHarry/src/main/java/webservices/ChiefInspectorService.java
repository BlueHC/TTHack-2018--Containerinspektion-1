package webservices;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLEngineResult.Status;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.ibm.websphere.jaxrs20.multipart.IAttachment;
import com.ibm.websphere.jaxrs20.multipart.IMultipartBody;

import model.ChiefInspectorAppStatus;
import model.ContainerInspection;
import model.InspectionImage;
//import repository.GroundPlanRepositories;
import model.InspectionImageType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;



@Path( "/main" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class ChiefInspectorService extends BaseService {
	
	final static String  FORMFIELD_DESCRIPTION = "description";
	final static String  FORMFIELD_FILE = "file";
	final static String  FORMFIELD_NAME = "name";

	final static String  INSPECTION_IMAGE_FILE_PATH = "inspections";
	
	ChiefInspectorAppStatus aplicationState = new ChiefInspectorAppStatus();

	//@Inject
	//GroundPlanRepositories repo;
	
	/*@GET
	@Path( "/all" )
	public Response getAll() {
		return Response.ok(repo.getAll()).build();
	}*/
	
	/*@GET
	@Path( "/get/{id}" )
	public Response get(@PathParam("id") int id) {
		return Response.ok(repo.get(id)).build();
	}*/

	@GET
	@Path("/inspection/new")
    public Response getImageNames() {
		ContainerInspection inspection = this.aplicationState.startNewInspection();
        return Response.ok("NEW_" + inspection.getId().toString() ).build();
    }	
	
	@POST
	@Path( "/image/add" )
	@Consumes("multipart/form-data")
	//@Produces("multipart/form-data")
	public Response add(IMultipartBody multipartBody) {
		ContainerInspection inspection = this.aplicationState.getLatestInspection();
		InspectionImage inspectionImage = new InspectionImage();
		inspectionImage.setId(getNextImageId(inspection));
		inspectionImage.setType(InspectionImageType.ORIGINAL);
		inspectionImage.setName( "inspection_" + inspection.getId().toString() + "_src_" + inspectionImage.getId().toString());//getNameFormFieldValue(multipartBody) );

		String uploadedFileName = getFileNameFromFileFormFieldByFieldName( multipartBody, FORMFIELD_FILE );
		String[] uploadedFileNameParts = uploadedFileName.split("\\."); 
		String uploadedFileNameExtension = uploadedFileNameParts.length > 0? uploadedFileNameParts[uploadedFileNameParts.length-1]: "";
		inspectionImage.setFilename( getGroundPlanFileName(inspectionImage.getName(), uploadedFileNameExtension) ); //The Name given by the user shall be the filename used to save the file on the server
		InputStream fileStream = getFormDataFieldValues(multipartBody, FORMFIELD_FILE).get(0);
		String filePath = saveFormDataStreamToFile( fileStream, inspectionImage.getFilename(), INSPECTION_IMAGE_FILE_PATH );
		inspectionImage.setFilename(filePath);
		inspection.addImage(inspectionImage);
		inspectionImage.setInspection(inspection);

		System.out.println(classifyTheImage(inspectionImage));
		try {
			evaluateImage(inspectionImage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		machScheisEgal(inspectionImage);
		return Response.ok(inspectionImage.getFilename()).build();
	}

	public ClassifiedImages classifyTheImage(InspectionImage inspectionImage){
		ClassifiedImages result = null;
        InputStream imagesStream;
		try {
			imagesStream = new FileInputStream(inspectionImage.getFilename());
		    ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		    .imagesFile(imagesStream)
		    .imagesFilename(inspectionImage.getFilename())
		    .parameters("{\"classifier_ids\": [\"ImageTypeModel_1176432070\"],\"threshold\": 0.6}")
		    .build();
		    result = this.aplicationState.getService().classify(classifyOptions).execute();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private int getNextImageId(ContainerInspection inspection) {
		return inspection.getImages() != null
				? inspection.getImages().size()
				:0;
	}

	public void machScheisEgal(InspectionImage inspectionImage){
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl(<BaseUrl Of Evaluation ML Service>)
				.build();
		ScheissEgalApi sheisEgal = retrofit.create(ScheissEgalApi.class);
		
		File file = new File(inspectionImage.getFilename());
		RequestBody imageRequestBody = RequestBody.create(okhttp3.MediaType.parse("image/*"), file);
		
		Part multiPartImg = Part.createFormData("file", inspectionImage.getFilename(), imageRequestBody);
		
		sheisEgal.uploadImage(multiPartImg, imageRequestBody).enqueue(new Callback<EvaluationResult>() {
			
			@Override
			public void onResponse(Call<EvaluationResult> arg0, retrofit2.Response<EvaluationResult> arg1) {
				EvaluationResult res = arg1.body();
				System.out.println(res);
			}
			
			@Override
			public void onFailure(Call<EvaluationResult> arg0, Throwable arg1) {
				System.out.println(arg1.getMessage());
			}
		});
	}
	
	private static final String TARGET_URL = "http://localhost:49158/rest/service/upload";
	public void evaluateImage(InspectionImage inspectionImage) throws Exception{
		// Connect to the web server endpoint
		URL serverUrl =
		    new URL(<BaseUrl Of Evaluation ML Service>);
		HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
		 
		String boundaryString = "----SomeRandomText";
		String fileUrl = inspectionImage.getFilename();
		File logFileToUpload = new File(fileUrl);
		 
		// Indicate that we want to write to the HTTP request body
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("POST");
		urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
		 
		OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
		BufferedWriter httpRequestBodyWriter =
		    new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));
		 
		// Include value from the myFileDescription text area in the post data
		httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
		httpRequestBodyWriter.write("Content-Disposition: form-data; name=\"file\"");
		httpRequestBodyWriter.write("\n\n");
		httpRequestBodyWriter.write("Log file for 20150208");
		 
		// Include the section to describe the file
		httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
		httpRequestBodyWriter.write("Content-Disposition: form-data;"
		        + "name=\"file\";"
		        + "filename=\""+ logFileToUpload.getName() +"\""
		        + "\nContent-Type: text/plain\n\n");
		httpRequestBodyWriter.flush();
		 
		// Write the actual file contents
		FileInputStream inputStreamToLogFile = new FileInputStream(logFileToUpload);
		 
		int bytesRead;
		byte[] dataBuffer = new byte[1024];
		while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
		    outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
		}
		 
		outputStreamToRequestBody.flush();
		 
		// Mark the end of the multipart http request
		httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
		httpRequestBodyWriter.flush();
		 
		// Close the streams
		outputStreamToRequestBody.close();
		httpRequestBodyWriter.close();
	}
	
	
	@GET
	@Path("/image/{filename}")
	@Produces( "image/jpg" )
    public Response getImageByFileName(@PathParam("filename") String filename) {
		return super.getImageByFileName(filename, INSPECTION_IMAGE_FILE_PATH);
    }	
	

	private String getGroundPlanFileName(String newGroundPlanName, String uploadedFileNameExtension) {
		String fileName = convertFreeTextToValidFileName(newGroundPlanName);
		return !uploadedFileNameExtension.isEmpty()? fileName + "." + uploadedFileNameExtension: fileName; 
  	}


	private String getFileExtensionOfFileFormField(IMultipartBody multipartBody) {
		String formElementName = getFormElementNameOfFormField(multipartBody, FORMFIELD_FILE);
		if( !formElementName.isEmpty() ){
			String[] nameParts = formElementName.split("."); 
			return nameParts.length > 0? nameParts[nameParts.length-1]: "";
		}
		return "";
	}


	private String convertFreeTextToValidFileName(String fileName) {
		fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_").trim().toLowerCase();
		return fileName.length() > 250? fileName.substring(0,250): fileName;
	}


	private String getNameFormFieldValue(IMultipartBody multipartBody) {
		return getFormDataValueFromFieldStream( getFormDataFieldValues(multipartBody, FORMFIELD_NAME).get(0) );
	}


	private String getDescriptionFormFieldValue(IMultipartBody multipartBody) {
		return getFormDataValueFromFieldStream( getFormDataFieldValues(multipartBody, FORMFIELD_DESCRIPTION).get(0) );
	}
	
	BufferedImage createResizedCopy(Image originalImage, 
            int scaledWidth, int scaledHeight, 
            boolean preserveAlpha)
    {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
        g.dispose();
        return scaledBI;
    }	
		
}
