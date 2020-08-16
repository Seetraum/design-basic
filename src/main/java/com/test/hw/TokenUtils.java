package com.test.hw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @date 2020/05/14
 */
public class TokenUtils {

  private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);
  private static RestTemplate restTemplate = new RestTemplate();
  private static ObjectMapper mapper = new ObjectMapper();

  public static String token(String user, String password) {
    String authJson = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\"" +
        ":{\"user\":{\"name\":${userName},\"password\":${password},\"domain\"" +
        ":{\"name\":${domain}}}}},\"scope\":{\"project\":{\"name\":\"${projectId}\"}}}}";
    authJson = authJson.replace("${domain}", user)
        .replace("${userName}", user)
        .replace("${projectId}", "cn-south-1")
        .replace("${password}", password);
    return getToken(authJson);
  }

  private static String getToken(String json) {
    logger.info("Account info = {}", json);
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json");
    HttpEntity<String> jsonEntity = new HttpEntity<String>(json, header);
    ResponseEntity<String> resp = restTemplate
        .postForEntity("https://iam.myhuaweicloud.com/v3/auth/tokens", jsonEntity, String.class);
    List<String> headers = resp.getHeaders().get("X-Subject-Token");
    if (headers != null && headers.size() > 0) {
      return headers.get(0);
    }
    return null;
  }

  public static String projectId(String user, String password) {
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json");
    header.add("X-Auth-Token", token(user, password));
    HttpEntity<String> requestEntity = new HttpEntity<String>(null, header);
    ResponseEntity<String> resp = restTemplate
        .exchange("https://iam.myhuaweicloud.com/v3/projects", HttpMethod.GET, requestEntity,
            String.class);
    String projectId = "";
    try {
     Map<String, List>  infoMap =  mapper.readValue(resp.getBody(), Map.class);
     List<LinkedHashMap<String,String>> projects = infoMap.get("projects");
     for (LinkedHashMap<String,String> project : projects){
       if (project.get("name").contains("cn-south-1")){
         projectId = project.get("id");
         break;
       }
     }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return projectId;
  }
}
