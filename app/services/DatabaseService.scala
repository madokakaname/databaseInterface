package services

import java.sql.{Connection, DriverManager}

import scala.collection.mutable.ListBuffer

/**
  * Created by Sergei on 07.12.2016.
  */
class DatabaseService {

  def select(query: String) : List[List[String]] = {
    // connect to the database named "mysql" on the localhost
    val driver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val username = "postgres"
    val password = "admin"
    val result = new ListBuffer[List[String]]()
    //    var numberOfColumns = new Int

    // there's probably a better way to do this
    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(query)
      val rsmd = resultSet.getMetaData
      val tempList = new ListBuffer[String]
      for (i <- 1 to rsmd.getColumnCount)
        tempList.append(rsmd.getColumnName(i))
      result.append(tempList.toList)
      while ( resultSet.next() ) {
        tempList.clear()
        for (i <- 1 to rsmd.getColumnCount)
          tempList.append(resultSet.getString(i))
        //        val nameToNationality =  (resultSet.getString("name"), resultSet.getString("nationality"))
        //        println(nameToNationality)
        result.append(tempList.toList)
      }
    } catch {
      case e: Exception => return List(List(e.getMessage))

    }

//    result.map(list => println(list))
    //    println(result.toList)
    connection.close()
    result.toList
  }

}
