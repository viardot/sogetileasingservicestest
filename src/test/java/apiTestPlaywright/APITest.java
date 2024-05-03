package apiTestPlaywright;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class APITest {
	
	Playwright playwright;
	APIRequestContext apiRequestContext;
	
	@BeforeClass
	public void setup() {
		
	    //header
	    Map<String, String> header = new HashMap<>();
	    header.put("Content-Type", "application/json");
	
	    playwright = Playwright.create();
	    apiRequestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
			.setBaseURL("http://172.24.218.143:8080")
			.setExtraHTTPHeaders(header));
	}
	
	@Test
	public void testAPISogetiLeasingServices() {
		
		//GET
		Integer responseStatus = apiRequestContext.get("/").status();		
		Assert.assertEquals(responseStatus, 200);
		
		//POST
		Map<String, Object> payload = new HashMap<>();
		payload.put("description", "Volvo V60");
		payload.put("price", 60000.00);
		
		responseStatus = apiRequestContext.post("/product", RequestOptions.create().setData(payload)).status();
		Assert.assertEquals(responseStatus, 201);
		
		//GET
		responseStatus = apiRequestContext.get("/product/1").status();		
		Assert.assertEquals(responseStatus, 200);
		
	}
	
	@AfterClass
	public void teardown() {
		apiRequestContext.dispose();
		playwright.close();
	}

}
