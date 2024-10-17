## 所有自己本地的配置文件不要提交！！！
## 所有自己本地的配置文件不要提交！！！
## 所有自己本地的配置文件不要提交！！！

### 模块说明
~~~
com.wr     
├── admin-ui           // 通用端ui [8083]
├── estate-ui          // 物业端ui [8084]
├── aiot-ui            // AIoT端ui [8085]
├── govern-ui          // 社区治理ui [8086]
├── wr-gateway         // 网关模块 [9100]
├── wr-auth            // 认证中心 [9200]
├── wr-modules         // 业务模块
│       └── modules-job                            // 定时任务 [9307]
│       └── modules-file                           // 文件服务 [9300]
│       └── modules-system                         // 智慧运营平台 [9301]
│       └── modules-estate                         // 智慧物管平台 [9302]
│       └── modules-aiot                           // 物联网应用一体化平台 [9303]
│       └── modules-govern                         // 小区治理平台 [9304]
│       └── modules-payment                        // 支付模块 [9305]
│       └── modules-work                           // 物业工单模块 [9306]---暂时不用
├── wr-api             // 接口模块 
│       └── api-base                               // 基础接口模块 [9401]---此模块不写接口
│       └── api-device                             // 硬件设备接口模块 [9402]
│       └── api-estate                             // 物业小程序接口模块 [9403]
│       └── api-owner                              // 业主H5接口模块 [9404]
│       └── api-payment                            // 停车缴费接口模块 [9405]
├── wr-common          // 通用工具模块
│       └── common-core                            // 核心模块
│       └── common-datascope                       // 权限范围
│       └── common-datasource                      // 多数据源
│       └── common-log                             // 日志记录
│       └── common-redis                           // 缓存服务
│       └── common-security                        // 安全模块
│       └── common-swagger                         // 系统接口
│       └── common-customize                       // 自定义模块，所有自定义的类放到此模块中
├── wr-remote          // 模块间接口调用模块
│       └── remote-system                          // 接口调用处理
├── wr-visual          // 图形化管理模块
│       └── visual-monitor                         // 监控中心 [9500]
├──pom.xml                // 公共依赖
~~~

### 接口数据传输及解密
~~~
1、所有接口方法以参数 String ciphertext来接收所有参数
2、在方法首行添加如下代码，用于参数解密：

        // 解密
        Object object = DecryptUtil.decrypt**(ciphertext, Object.class);
        if(null == object){
            return AjaxResult.error("系统错误，请重试！！");
        }
 其中：Object表示业务代码中需要的数据类型，String、DTO、entity、JSONObject（Long、Integer先用String，再强转）
       DecryptUtil 内有方法 decryptBase64、decryptRsa 和 decryptAes 三个解密方法，根据项目需求选择使用
~~~

### 注解使用说明
~~~
1、@Log：开发后台时，在controller层，除get请求外，在对应方法上添加此注解，用于记录操作日志
    如： @Log(title = "参数管理", businessType = BusinessType.INSERT)
    
2、@RepeatSubmit：用于防止重复提交
      
3、@ApiLog：开发接口时，在controller层，除get请求外，在对应方法上添加此注解，用于记录操作日志
    如： @ApiLog(title = "参数管理", businessType = BusinessType.INSERT)

4、@FieldRepeat：可在新增或修改时使用，用于校验参数是否存在，仅用于后台

5、@AuthRequired：用于校验是否登录，根据具体功能使用，仅开发接口时使用

6、@EncryptResponse：在所有接口方法上添加此注解，用于数据加密后返回

7、@PrimaryKey：标明字段为主键；@ParentKey：标明字段为父id；@ChildrenKey：标明字段为子集
    此三个注解搭配 TreeUtils 使用

8、@SensitiveWord：用于标识字段需敏感词过滤，搭配 SensitiveUtil 使用

注：用于接口端的实体类中,Date类型的字段上添加加如下两个注解
    @JsonFormat(pattern = "xxxxxx")
    @JSONField(format = "xxxxxx")
~~~

### 工具类使用说明
~~~
1、BaseUtils：所有工具类继承一下这个工具类，可以省去定义 org.slf4j.Logger

2、JsonUtil：用于JSON的判断及转换，具体见方法

3、HttpClientUtil：HTTP请求

4、Base64File：用于 Base64 串转 MultipartFile

5、AesUtil：AES加密解密

6、RsaUtil：RSA加密解密

7、QrCodeUtils：二维码的生成及解析

8、ThumbnailsUtils：用于文件的压缩

9、TreeUtils：树状结构的生成

10、TableUtils：用于动态获取表名

~~~
