@file:Suppress("unused", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.assynu.shoppinglist

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal object Database {
    @SuppressLint("StaticFieldLeak")
    private val db = Firebase.firestore

    fun addProduct(Name: String){

        val product = hashMapOf(
            "Purchased" to false,
            "Name" to Name,
        )

        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun removeProduct(document: String)
    {
        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List").document(document)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

//    fun getProducts() {
//        val products = ArrayList<Product>()
//
//
//        GlobalScope.launch(Dispatchers.IO) {
//            db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        val data = document.data.toList().toTypedArray()
//                        val product = Product(document.id, data[0].second as Boolean, data[1].second as String)
//                        products.add(product)
////                        println("${document.id} | ${data[1]} | ${data[1]}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    println("Error getting documents. $exception")
//                }
//            println(products)
////            delay(1000L)
////            showProducts(products)
//        }
//    }

//    TODO -> Real time listening for database updates
//    fun listenForUpdates()
//    {
//        val docRef = db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
//        docRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null && snapshot.exists()) {
//                Log.d(TAG, "Current data: ${snapshot.data}")
//            } else {
//                Log.d(TAG, "Current data: null")
//            }
//        }
//    }
}
