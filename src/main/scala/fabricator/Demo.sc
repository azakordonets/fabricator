import org.joda.time.{DateTime, IllegalFieldValueException}
import play.api.libs.json.{Json, JsValue}

val startDate = new DateTime()
val endDate = new DateTime().plusDays(0)plusYears(0)


val json: JsValue = Json.parse("{\"start\": {\n    \"year\": 2001,\n    \"month\": 1,\n    \"day\": 1,\n    \"hour\": 0,\n    \"minute\": 0\n  },\n  \"end\": {\n    \"year\": 2010,\n    \"month\": 1,\n    \"day\": 1,\n    \"hour\": 0,\n    \"minute\": 0\n  },\n  \"step\": {\n    \"year\": 1,\n    \"month\": 1,\n    \"day\": 1,\n    \"hour\": 0,\n    \"minute\": 0\n  }}")
val start: JsValue = json \ "start"
val format = (json \ "format").asOpt[JsValue]

new DateTime(1,1,1,1,1)


  