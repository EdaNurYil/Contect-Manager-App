package com.example.contectmanagerapp.viewModel

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contectmanagerapp.room.User
import com.example.contectmanagerapp.room.UserRepository
import kotlinx.coroutines.launch

 class UserViewModel(private val repository: UserRepository) : ViewModel(), {


    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: User

    @Bindable
    //go to main activity and add for new text for name and email for the user
    //@={userViewModel.inputName} and @={userViewModel.inputEmail}
    //to make it work we need to add this interface androidx.databinding.Observable
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        saveOrUpdateButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {

            //make update
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)

        } else {

            //insert functinality
            val name = inputName.value!! //not null !!
            val email = inputEmail.value!!

            insert(User(0, name, email))

            inputName.value = null
            inputEmail.value = null
        }

    }


    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun clearAllOrDelete() {
        if(isUpdateOrDelete){
            delete(userToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun clearAll() = viewModelScope.launch {

        repository.deleteAll()

    }

    fun update(user: User) = viewModelScope.launch {
        repository.update(user)

        //resetting the Buttons and Fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)

        //resetting the Buttons and Fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateOrDelete(user : User) {

        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }
}