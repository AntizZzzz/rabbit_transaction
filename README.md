"# rabbit_transaction" 
描述：
基于rabbitmq和本地消息表实现分布式事务
项目架构：
springboot、nacos、rabbitmq、redis
yml配置文件：
rabbit-server.yml
server:
  shutdown: graceful
spring:
  lifecycle:
    ## 优雅停机宽限期时间
    timeout-per-shutdown-phase: 30s
  datasource:
    druid:
      # JDBC 配置(驱动类自动从url的mysql识别,数据源类型自动识别)
      url: jdbc:mysql://127.0.0.1:3306/rabbit_server?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8&autoReconnect=true&useAffectedRows=true
      username: admin
      # 需要加密秘闻，不用的话把com.xjw.config.JasyptConfig注释掉或者不注入即可
      password: ENC(GX4vsBpiS45B/KU4ck7DSInz9Z/UcxPVuecF6+K9IxubMlKNuW5zyYB1Z6EWO3nM)
      driver-class-name: com.mysql.cj.jdbc.Driver
      initial-size: 1
      max-active: 20
      min-idle: 1
      max-wait: 60000
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      validation-query: SELECT 'x'
      test-on-borrow: false
      test-on-return: false
      test-while-idle: true
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      filters: stat
      web-stat-filter.enabled: true
      web-stat-filter.url-pattern:  /*
      web-stat-filter.exclusions: "*.js , *.gif ,*.jpg ,*.png ,*.css ,*.ico , /druid/*"
      web-stat-filter.session-stat-max-count: 1000
      web-stat-filter.profile-enable: true
      stat-view-servlet.enabled: true
      stat-view-servlet.url-pattern:  /druid/*
      stat-view-servlet.reset-enable:  true
      stat-view-servlet.login-username: admin
      stat-view-servlet.login-password: admin
  ################### mysq end ##########################
  kafka:
    bootstrap-servers: 172.16.10.2:9092

management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: ALWAYS

rabbitmq.yml
spring:
    rabbitmq:
        addresses: 172.16.15.3
        port: 5672
        username: admin
        password: ENC(ofVRu6dQByUYJdbsGYbq2HcqJIIkR6bj/JhkLO7OQUpaWxQ7ru4mq/XOANH6th+M)
        publisher-confirms: true
        publisher-confirm-type: correlated
    
    
redis.yml
spring:
  redis:
    ################### redis 单机版 start ##########################
    host: 127.0.0.1
    password: ENC(TfsiXwZLxeC5ZZ3A+wFyfA+4YvmFaB/SOilpFR1u0zauBRx5N2FZ7wBjyFJ8POn/)
    port: 6379
    timeout: 6000
    database: 2
    lettuce:
      pool:
        max-active: 10 # 连接池最大连接数（使用负值表示没有限制）,如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
        max-idle: 8   # 连接池中的最大空闲连接 ，默认值也是8
        max-wait: 100 # # 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException  
        min-idle: 2    # 连接池中的最小空闲连接 ，默认值也是0
      shutdown-timeout: 100ms
      ################### redis 单机版 start ##########################
