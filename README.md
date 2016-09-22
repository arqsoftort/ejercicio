# ejercicio

## Installation

Before importing the project into NetBeans, you should start your Glassfish server and:

#### Create a JDBC Connection Pool

```
create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource --restype javax.sql.DataSource --property portNumber=1527:password=jeeexample:user=jeeexample:serverName=localhost:databaseName=jeeexample:connectionAttributes=\;create\\=true jee_example_pool
```

#### Create a JDBC Resource

```
create-jdbc-resource --connectionpoolid jee_example_pool jdbc/jee-example-pool
```

#### Create a JMS Connection Factory

```
create-jms-resource --restype javax.jms.ConnectionFactory jms/ConnectionFactory
```

#### Create a JMS Queue

```
create-jms-resource --restype javax.jms.Queue --property Name=Queue jms/Queue
```
