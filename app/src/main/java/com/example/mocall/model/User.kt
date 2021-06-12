package com.example.mocall.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize
import java.lang.Exception

@Parcelize
data class User
    (val uid:String,
     val name:String,
     val phoneNumber:String,
     val profileImage:String): Parcelable {
        companion object{
            fun DocumentSnapshot.toUser():User?{
                return try{
                    val name=getString("name")!!
                    val profileImage=getString("profile_image")!!
                    val phoneNumber=getString("phone_number")!!
                    User(id,name,phoneNumber,profileImage)
                }catch (e:Exception){
                    Log.e("User Model", "Error converting user profile", e)
                    FirebaseCrashlytics.getInstance().log("Error converting user profile")
                    FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
                    FirebaseCrashlytics.getInstance().recordException(e)
                    null
                }
            }
        }
     }