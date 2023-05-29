package cz.cvut.fit.poliskyr.trainmeapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.navigation.Screen
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.*

@Composable
fun HeaderBar(username: String){
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Icon(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App icon",
            modifier = Modifier.size(96.dp),
        )
        Column(
            modifier = Modifier
                .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.account),
                contentDescription = "Account",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = username,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Dark
            )
        }
    }
}

@Composable
fun SideNavIcon(screen: Screen){
    Icon(
        painter = painterResource(id = screen.painterId),
        contentDescription = screen.route,
        modifier = Modifier.size(40.dp)
    )
    Text(
        text = screen.title,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(start = 20.dp, top = 12.dp)
    )
}

@Composable
fun LogOutFooter(onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onLogout() }
            .padding(bottom = 24.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = "Logout",
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = stringResource(id = R.string.logout),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 20.dp)
        )
    }
}

@Composable
fun InfoColumn(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoLargeText(text = stringResource(id = R.string.address))
            Spacer(Modifier.height(16.dp))
            InfoMediumText(text = stringResource(id = R.string.address_1))
            Spacer(Modifier.height(5.dp))
            InfoMediumText(text = stringResource(id = R.string.praha))
        }
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoLargeText(text = stringResource(id = R.string.phone))
            Spacer(Modifier.height(16.dp))
            InfoMediumText(text = stringResource(id = R.string.phone_1))
            Spacer(Modifier.height(5.dp))
            InfoMediumText(text = stringResource(id = R.string.phone_2))
        }
    }
    Spacer(Modifier.height(32.dp))
    InfoLargeText(text = stringResource(id = R.string.email))
    Spacer(Modifier.height(16.dp))
    InfoMediumText(text = stringResource(id = R.string.email_1))
    Spacer(Modifier.height(5.dp))
    InfoMediumText(text = stringResource(id = R.string.email_2))
}

@Composable
fun EmailColumn(){
    Text(
        text = stringResource(id = R.string.send_message),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(18.dp))
    InfoSmallText(text = stringResource(id = R.string.send_info_message_1))
    Spacer(Modifier.height(5.dp))
    InfoSmallText(text = stringResource(id = R.string.send_info_message_2))
    Spacer(Modifier.height(18.dp))
}

@Composable
fun InfoMediumText(text: String){
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        color = InfoDark
    )
}

@Composable
fun InfoLargeText(text: String){
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun InfoSmallText(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = InfoDark
    )
}


@Composable
fun InfoHome(){
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.title_app_name),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_p_1),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(12.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_p_2),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(24.dp))
}

@Composable
fun InfoTitles(){
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_info_1),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_info_2),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_info_3),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_info_4),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(24.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_info_5),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        color = InfoDark,
        modifier = Modifier.width(400.dp)
    )
    Spacer(Modifier.height(48.dp))
}

@Composable
fun InfoTeam(){
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_team_1),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Image(
        painter = painterResource(id = R.drawable._team),
        contentDescription = "Team",
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .fillMaxSize(),
        contentScale = ContentScale.FillWidth
    )
    Spacer(Modifier.height(5.dp))
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_team_2),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    androidx.compose.material3.Text(
        text = stringResource(id = R.string.home_team_3),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        color = InfoDark
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
fun TrainingTimeColumn(date: String, timeStart: String, timeEnd: String){
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 15.dp, bottom = 16.dp),
    ) {
        TrainingInfoText(infoText = stringResource(id = R.string._date), text = date)
        Spacer(modifier = Modifier.height(32.dp))
        TrainingInfoText(infoText = stringResource(id = R.string._start), text = timeStart)
        Spacer(modifier = Modifier.height(32.dp))
        TrainingInfoText(infoText = stringResource(id = R.string._end), text = timeEnd)
    }
}

@Composable
fun TrainingMainColumn(trainerUsername: String, status: String, onDelete: () -> Unit){
    Column(
        modifier = Modifier
            .padding(start = 0.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
    ) {
        androidx.compose.material3.Text(
            text = stringResource(id = R.string._trainer),
            style = MaterialTheme.typography.titleLarge,
            color = Dark,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.ExtraBold
        )
        androidx.compose.material3.Text(
            text = trainerUsername,
            style = MaterialTheme.typography.titleLarge,
            color = Dark,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(32.dp))
        androidx.compose.material3.Text(
            text = status,
            style = MaterialTheme.typography.titleLarge,
            color = (when (status) {
                stringResource(id = R.string.in_progress) -> {
                    Warning
                }
                stringResource(id = R.string.will_be) -> {
                    WillBe
                }
                else -> {
                    Danger
                }
            }),
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(32.dp))
        androidx.compose.material3.Text(
            text = stringResource(id = R.string.delete_training),
            style = MaterialTheme.typography.titleLarge,
            color = Danger,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onDelete() }
        )
    }
}

@Composable
fun TrainingInfoText(infoText: String, text: String){
    androidx.compose.material3.Text(
        text = infoText,
        style = MaterialTheme.typography.titleLarge,
        color = Dark,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.ExtraBold
    )
    androidx.compose.material3.Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Dark,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainerCardInfo(trainer: Trainer, onClick: () -> Unit){
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxSize(),
    ) {
        androidx.compose.material3.Text(
            modifier = Modifier.padding(start = 12.dp),
            text = trainer.username,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Dark
        )

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.Text(
            modifier = Modifier.padding(start = 12.dp),
            text = stringResource(id = R.string.rating_reviews) + trainer.reviewValue.toString(),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Dark
        )

        Spacer(modifier = Modifier.height(16.dp))

        PricesRow(trainer = trainer, onClick = onClick)

        Spacer(Modifier.size(16.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.LightGray,
            thickness = 1.dp
        )
        TrainerInfoBar(info = trainer.email, icon = Icons.Default.Email)

        TrainerInfoBar(info = trainer.telephone, icon = Icons.Default.Phone)

        TrainerInfoBar(info = trainer.dateOfBirthday, icon = Icons.Default.DateRange)
    }
}

@Composable
fun PricesRow(trainer: Trainer, onClick: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.single_access),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Dark,
            )
            Button(
                onClick = {
                    onClick()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Warning,
                    contentColor = Dark
                ),
            ) {
                androidx.compose.material3.Text(
                    text = trainer.priceForOneTraining,
                    //maxLines = 1,
                    //overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                    color = Dark
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.month_access),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Dark
            )
            Button(
                onClick = {
                    onClick()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Warning,
                    contentColor = Dark
                ),
            ) {
                androidx.compose.material3.Text(
                    text = trainer.priceForMonthTraining,
                    //maxLines = 1,
                    //overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                    color = Dark
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainerInfoBar(info: String, icon: ImageVector){
    TextField(
        value = info,
        onValueChange = {},
        readOnly = true,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "info icon",
                tint = Warning
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Warning.copy(alpha = ContentAlpha.medium),
            textColor = Dark,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            containerColor = Color.Transparent
        ),
    )
}