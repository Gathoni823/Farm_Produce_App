package com.example.farmproduceapp.data

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.farmproduceapp.models.Farmer
import com.example.farmproduceapp.navigation.ROUTE_VIEW_FARMER
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FarmerViewModel():ViewModel() {
    private val  _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading

    private val  _errorMessage = MutableLiveData<String>()
    val errorMessage:LiveData<String> get() = _errorMessage

    private val  _successMessage = MutableLiveData<String>()
    val successMessage:LiveData<String> get() = _successMessage

    fun saveFarmer(firstname: String,lastname: String,gender: String,age: String,
                   navController: NavController,context: Context){
        val id = System.currentTimeMillis().toString()
        val dbRef = FirebaseDatabase.getInstance().getReference("Farmer/$id")
        val farmerData = Farmer("", firstname, lastname, gender, age, id)
        dbRef.setValue(farmerData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    showToast("Farmer added successfully", context)
                    navController.navigate(ROUTE_VIEW_FARMER)

                }else {
                    showToast("Farmer not added", context)
                }
            }
    }
    fun viewFarmer(farmer:MutableState<Farmer>,
                    farmers:SnapshotStateList<Farmer>,context: Context):
            SnapshotStateList<Farmer>{
        val ref = FirebaseDatabase.getInstance().getReference()
            .child("Farmer")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                farmers.clear()
                for (snap in snapshot.children){
                    val value = snap.getValue(Farmer::class.java)
                    farmer.value = value!!
                    farmers.add(value)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to fetch farmers", context)
            }
        })
        return farmers
    }
    fun updateFarmer(context: Context, navController: NavController, firstname: String, lastname: String, gender: String, age: String, id: String){
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Farmer/$id")
        val updatedFarmer = Farmer("",firstname, lastname, gender, age, id)

        databaseReference.setValue(updatedFarmer)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    showToast("Farmer Updated Successfully",context)
                    navController.navigate(ROUTE_VIEW_FARMER)
                }else{
                    showToast("Record update failed",context)
                }

            }

    }
    fun deleteFarmer(context: Context, id: String, navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Farmer")
            .setMessage("Are you sure you want to delete this Farmer?")
            .setPositiveButton("Yes"){_, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Farmer/$id")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if(task.isSuccessful){
                        showToast("Farmer deleted",context)
                    }else{
                        showToast("Farmer not deleted",context)
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    public fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}



