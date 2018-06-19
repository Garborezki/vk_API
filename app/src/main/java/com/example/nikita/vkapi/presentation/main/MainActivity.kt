package com.example.nikita.vkapi.presentation.main


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nikita.vkapi.R
import com.example.nikita.vkapi.other.FragmentCreator
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError


class MainActivity : AppCompatActivity() {

    val fragmentCreator = FragmentCreator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VKSdk.login(this, VKScope.WALL)
        navigateTo(FragmentCreator.FragmentFabric.NEWS_LIST)

    }

    fun navigateTo(fragmentFabric: FragmentCreator.FragmentFabric, bundle: Bundle = Bundle(),
                   isPutBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,
                fragmentCreator.createFragment(fragmentFabric, bundle))
        if (isPutBackStack) {
            transaction.addToBackStack(fragmentFabric.name)
        }

        transaction.commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {


                    override fun onResult(res: VKAccessToken) {


                        /*  val newsList = ArrayList<NewsModel>()

                          val request = VKApiWall().get(VKParameters.from(VKApiConst.OWNER_ID, "-77522551", VKApiConst.COUNT, 15))
                          request.executeWithListener(object : VKRequest.VKRequestListener() {

                              override fun onComplete(response: VKResponse?) {
                                  try {
                                      val jsonObjects = response!!.json.get("response") as JSONObject
                                      val jsonArray  = jsonObjects.get("items") as JSONArray
                                      val currentNews = NewsModel()
                                      for (i in 0..jsonArray.length()) {
                                          val currentItem = jsonArray.get(i) as JSONObject
                                          currentNews.content = currentItem.getString("text")
                                          newsList.add(currentNews)
                                      }
                                  }catch (e: Exception){
                                      e.printStackTrace()
                                  }
                              }

                              override fun onError(error: VKError?) {
                                  //Do error stuff
                              }

                              override fun attemptFailed(request: VKRequest?, attemptNumber: Int, totalAttempts: Int) {
                                  //I don't really believe in progress
                              }
                          })
                             */
                    }

                    override fun onError(error: VKError) {

                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
