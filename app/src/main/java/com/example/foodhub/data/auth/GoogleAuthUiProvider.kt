package com.example.foodhub.data.auth

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.foodhub.constant.WEB_CLIENT_ID
import com.example.foodhub.data.models.GoogleAccount
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthUiProvider {

    suspend fun signIn(
        credentialManager: CredentialManager,
        activityContext: Context
    ): GoogleAccount? {

        return try {
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(WEB_CLIENT_ID)
                .build()

            val result = credentialManager.getCredential(
                context = activityContext,
                request = getCredentialRequest(),
            ).credential

            handleGoogleCredential(result)
        } catch (e: androidx.credentials.exceptions.GetCredentialException) {
            // This happens when user cancels or there's an issue fetching credentials
            Log.e("GoogleAuthUiProvider", "Sign-in cancelled or failed: ${e.message}")
            null
        } catch (e: Exception) {
            // Any other unexpected error
            Log.e("GoogleAuthUiProvider", "Unexpected error: ${e.message}")
            null
        }
    }

    private fun getCredentialRequest(): GetCredentialRequest {

        return GetCredentialRequest.Builder()
            .addCredentialOption(
                GetSignInWithGoogleOption.Builder(
                    WEB_CLIENT_ID
                ).build()
            )
            .build()

    }

    fun handleGoogleCredential(credential: Credential): GoogleAccount {

        when {
            credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {

                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                Log.d("handleGoogleCredential", "handleGoogleCredential: $googleIdTokenCredential ")
                return GoogleAccount(
                    token = googleIdTokenCredential.idToken,
                    displayName = googleIdTokenCredential.displayName ?: "",
                    profileImageUrl = googleIdTokenCredential.profilePictureUri.toString()
                )

            }


            else -> {
                throw IllegalStateException("Invalid Credentials")
            }
        }
    }

}
