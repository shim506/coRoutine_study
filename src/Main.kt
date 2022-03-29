import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//
//// 최초 코드 17 초
// 사진 개수 103
//CoroutineScope(Dispatchers.Main).launch {
//    val str = AssetLoader.jsonToString(applicationContext, DOODLE_FILE_NAME)
//    val jsonArray = JSONArray(str)
//    val start = System.currentTimeMillis();
//
//    repeat(jsonArray.length()) {
//        val json = jsonArray.getJSONObject(it)
//        val title = json.getString("title")
//        val image = json.getString("image")
//        val date = json.getString("date")
//
//        val bitmap = withContext(Dispatchers.IO) {
//            loadImage(image)
//        }
//        photos.add(Photo(title, bitmap, date))
//    }
//    val end = System.currentTimeMillis()
//    val time = (end - start) / 1000
//    Log.d(TAG, "사진 개수 : ${photos.size.toString()}")
//    Log.d(TAG, "쇼요시간  : $time")
//}

//소요시간 2초
//CoroutineScope(Dispatchers.IO).launch {
//    val str = AssetLoader.jsonToString(applicationContext, DOODLE_FILE_NAME)
//    val jsonArray = JSONArray(str)
//    val start = System.currentTimeMillis();
//    val job = launch {
//        repeat(jsonArray.length()) {
//            val json = jsonArray.getJSONObject(it)
//            val title = json.getString("title")
//            val image = json.getString("image")
//            val date = json.getString("date")
//
//            launch(Dispatchers.IO) {
//                val bitmap = loadImage(image)
//                photos.add(Photo(title, bitmap, date))
//            }
//        }
//    }
//    job.join()
//    val end = System.currentTimeMillis()
//    val time = (end - start) / 1000
//    Log.d(TAG, "사진 개수 : ${photos.size.toString()}")
//    Log.d(TAG, "쇼요시간  : $time")
//}
