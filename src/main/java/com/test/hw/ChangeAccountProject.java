package com.test.hw;

import com.huawei.openstack4j.api.OSClient.OSClientV3;
import com.huawei.openstack4j.model.common.Identifier;
import com.huawei.openstack4j.model.identity.v3.Project;
import com.huawei.openstack4j.openstack.OSFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeAccountProject {
  private static final Logger LOG = LoggerFactory.getLogger(ChangeAccountProject.class);
  private static final List<String> sqls = new ArrayList<>();
  public static void main(String[] args) throws IOException {

    Files.readAllLines(Path.of("D:/project/design-basic/account.csv")).forEach(line ->{
      var userInfo = line.split(",");
      var user = userInfo[0];
      var password = userInfo[1];
      OSClientV3 accountClient = OSFactory.builderV3().endpoint("https://iam.myhuaweicloud.com/v3")
          .credentials(user, password, Identifier.byName(user))
          .scopeToDomain(Identifier.byName(user)).authenticate();

      List<? extends Project> projects = accountClient.identity().projects().getByName("cn-south-1");
      if (projects != null && projects.size() == 1) {
        String project = projects.get(0).getId();
        sql(user,project);
        return;
      }
      try {
        Project project =
            accountClient.identity().projects().create(accountClient.getToken().getDomain().getId(), "cn-south-1", "", true);
        sql(user,project.getId());
      } catch (Exception e) {
        LOG.warn("创建project出错，account:{}", accountClient.getToken().getDomain().getName());
      }
    });
    sqls.forEach(s -> System.out.println(s));
  }

  private static void sql(String account, String projectId){
    String sql = "UPDATE member_account set project_id = '" + projectId + "' WHERE account_name = '" + account + "';";
    sqls.add(sql);
  }
}
