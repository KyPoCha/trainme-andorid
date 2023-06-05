package cz.cvut.fit.poliskyr.trainmeapp.screen

import android.Manifest
import android.os.Build
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import cz.cvut.fit.poliskyr.trainmeapp.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    rationale: String,
    permissionNotAvailableContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val permissions: MutableList<String> = mutableListOf(Manifest.permission.CAMERA)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions += Manifest.permission.POST_NOTIFICATIONS
    }
    val permissionState = rememberMultiplePermissionsState(permissions)

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            AlertDialog(
                onDismissRequest = { },
                title = { Text(text = stringResource(id = R.string.permission_request)) },
                text = { Text(rationale) },
                confirmButton = {
                    Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            )
        },
        permissionsNotAvailableContent = permissionNotAvailableContent,
        content = content
    )
}
