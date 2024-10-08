package com.example.dummy.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dummy.User
import com.example.dummy.screens.pages.Pages
import com.example.dummy.viewModels.MainViewModel

@Composable
fun SignUpScreen(modifier: Modifier = Modifier , viewModel: MainViewModel , navController: NavController) {
    val user = User(
        name = viewModel.name.value,
        email = viewModel.email.value,
        phoneNumber = viewModel.phoneNumber.value,
        isfarmer = viewModel.isfarmer.value
    )
    val name = viewModel.name
    val email = viewModel.email
    val password = viewModel.password
    val isFarmer = viewModel.isfarmer

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            label = { Text(text = "Enter Name") }
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = { Text(text = "Enter Email") }
        )

        var passwordVisible by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Enter Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                // Toggle the password visibility
                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, "")
                }
            },
            modifier = Modifier.padding(12.dp)
        )

        Button(onClick = {
            user.isfarmer = true
            viewModel.linkPhoneCredential(user, onResult = {  sucess->
                if(sucess){
                    navController.navigate(Pages.FarmerDataPage.route)
                }
                else{
                    Log.d("fault" , "SignUpfarmer fault")
                }
            })
        }) {
            Text(text = "Sign Up as farmer")
        }

        Button(onClick = {
            user.isfarmer = false
            viewModel.linkPhoneCredential(user, onResult = {  sucess->
                if(sucess){
                    navController.navigate(Pages.DataPage.route)
                }
                else{
                    Log.d("fault" , "SignUpCustomer fault")
                }
            })
        }) {
            Text(text = "Sign Up as Customer")
        }
    }
}