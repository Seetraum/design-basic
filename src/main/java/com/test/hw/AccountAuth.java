package com.test.hw;

import com.huawei.openstack4j.api.OSClient;
import com.huawei.openstack4j.model.common.Identifier;
import com.huawei.openstack4j.openstack.OSFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class AccountAuth {
    public static void main(String[] args) throws IOException {
        AccountAuth accountAuth = new AccountAuth();
        accountAuth.unbindBucketsView(accountAuth.getAccountInfos());
    }

    private void unbindBucketsView(List<AccountInfo> accountInfos){
        String url = "https://iam.myhuaweicloud.com/v3/domains/{domain_id}/groups/{group_id}/roles/29f982b29e4e46398292ad6281813f3c";
        Map<String, String> errMap = new HashMap<>();
        Map<String, Integer> codes = new HashMap<>();
        int count = 0;
        for (AccountInfo accountInfo : accountInfos) {
            String token = getOSClint(accountInfo).getToken().getId();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url1 = url.replace("{domain_id}", accountInfo.getDomainId()).replace("{group_id}",accountInfo.getGroupId());
            HttpDelete httpDelete = new HttpDelete(url1);
            httpDelete.setHeader("content-type", "application/json;charset=utf-8");
            httpDelete.setHeader("X-Auth-Token",token);
            try {
                CloseableHttpResponse httpResponse = httpClient.execute(httpDelete);
                int code = httpResponse.getStatusLine().getStatusCode();
                codes.put(accountInfo.accountName,code);
                if (code == 400){
                    errMap.put(accountInfo.getAccountName(), EntityUtils.toString(httpResponse.getEntity()));
                    continue;
                }
                count ++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        errMap.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + "=====" + entry.getValue());
        });
        codes.forEach((k,v) -> System.out.println("AccountNmae = " + k + "  code = " + v));
        System.out.println(count);
    }

    private List<AccountInfo> getAccountInfos() throws IOException {
        List<AccountInfo> accountInfos = new ArrayList<>();
        Files.readAllLines(Paths.get("D:/project/sandbox-resource-clean/account.csv")).stream()
                .map(line -> line.split(",")).forEach(arr -> {
            AccountInfo accountInfo = new AccountInfo();
            if (arr.length > 4){
                accountInfo.setAccountName(arr[0]);
                accountInfo.setPassword(arr[1]);
                accountInfo.setProjectId(arr[2]);
                accountInfo.setDomainId(arr[3]);
                accountInfo.setGroupId(arr[4]);
                accountInfos.add(accountInfo);
            }
        });
        return accountInfos;
    }

    private static OSClient.OSClientV3 getOSClint(AccountInfo authInfo) {
        System.out.println("AccountName = " + authInfo.getAccountName() + "    password = " + authInfo.getPassword());
        return OSFactory.builderV3().endpoint("https://iam.myhuaweicloud.com/v3")
                .credentials(authInfo.getAccountName(), authInfo.getPassword(),
                        Identifier.byName(authInfo.getAccountName()))
                .scopeToDomain(Identifier.byName(authInfo.getAccountName())).authenticate();
    }

    private class AccountInfo{
        private String accountName;
        private String password;
        private String projectId;
        private String domainId;
        private String groupId;

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }
}
