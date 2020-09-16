package com.test.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.mybatisplus.entity.User;
import com.test.mybatisplus.mapper.UserMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMapper {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void contextLoads(){
    List<User> users = userMapper.selectList(null);
    users.forEach(System.out::println);
  }

  @Test
  public void testInsert(){
    User user = new User();
    user.setName("Paulson 的测试");
    user.setAge(44);
    user.setEmail("135464564@qq.com");

    int result = userMapper.insert(user); // 自动生成ID
    System.out.println(user);
    System.out.println(result);
  }

  @Test
  public void testUpdate(){
    User user = new User();
    // 通过条件自动拼接动态sql
    user.setId(6L);
    user.setAge(55);
    user.setName("Paulson 的更新");
    userMapper.updateById(user);
  }
  @Test
  public void testWrapper1(){
    // 查询 name 不为空，邮箱不为空，年龄大于22
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.isNotNull("name")
        .isNotNull("email")
        .ge("age", 12);
    List<User> users = userMapper.selectList(wrapper);
    users.forEach(System.out::println);
  }
 //查询一个数据  selectOne  出现多个结果使用 selectList
  @Test
  public void test2(){
    // 查询 name 为 Sandy
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("name", "Sandy");
    User user = userMapper.selectOne(wrapper);
    System.out.println(user);
  }

  // 测试分页插件
  @Test
  public void testPage(){
    // 参数一：当前页
    // 参数二：页面大小
    Page<User> page = new Page<>(1, 3);
    userMapper.selectPage(page, null);

    page.getRecords().forEach(System.out::println);
    System.out.println(page.getTotal());
  }

  // 测试删除
  @Test
  public void testDeleteById(){
    userMapper.deleteById(6L);
    userMapper.deleteBatchIds(Arrays.asList(2, 3));
  }

  @Test
  public void testDeleteMap(){
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "Paulson 的测试");
    userMapper.deleteByMap(map);
  }

  @Test
  public void test3(){
    // 年龄在20-30之间
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.between("age", 20, 30);
    Integer count = userMapper.selectCount(wrapper);// 查询结果数目
    System.out.println(count);
  }

  @Test
  public void test4(){
    // 模糊查询：name 不包含 E 的, email t开头
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    // 左和右  t%
    wrapper.notLike("name", "S")
        .likeRight("email", "t");
    List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
    maps.forEach(System.out::println);
  }

  @Test
  public void test5(){
    // 内查询 IN
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    // id 在子查询中查出来
    wrapper.inSql("id", "select id from user where id < 3");
    List<Object> objects = userMapper.selectObjs(wrapper);
    objects.forEach(System.out::println);
  }
}
