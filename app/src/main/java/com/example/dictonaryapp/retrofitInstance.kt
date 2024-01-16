import com.example.dictonaryapp.dictionaryAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/"

    private fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApi : dictionaryAPI = getInstance().create(dictionaryAPI::class.java)


}