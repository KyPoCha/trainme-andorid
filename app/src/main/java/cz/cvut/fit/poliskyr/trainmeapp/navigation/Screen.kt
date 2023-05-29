package cz.cvut.fit.poliskyr.trainmeapp.navigation

import cz.cvut.fit.poliskyr.trainmeapp.R

sealed class Screen(val route: String, val title: String ,val painterId: Int) {
    object MainScreen : Screen("main", "Home" ,R.drawable.dashboard)
    object TrainersScreen : Screen("trainers", "Trainers" ,R.drawable.group)
    object TrainingsScreen : Screen("trainings", "My Trainings" ,R.drawable.list)
    object ContactsScreen : Screen("contacts", "Contacts" ,R.drawable.contacts)
    object AuthScreen : Screen("auth", "Auth", R.drawable.logo)
}