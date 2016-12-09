package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import services.DatabaseService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def databaseInterface = Action {
    Ok(views.html.databaseInterface("abc database test", null, ""))
  }

  def databaseInterface(query: String) = Action {
    if (query.equals("null")) {
      Ok(views.html.databaseInterface("abc database test", null, ""))
    } else {
    def dbService = new DatabaseService
    Ok(views.html.databaseInterface("abc database test", dbService.select(query), query))
  }
  }

}
