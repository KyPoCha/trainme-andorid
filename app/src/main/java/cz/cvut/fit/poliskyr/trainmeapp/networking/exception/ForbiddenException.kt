package cz.cvut.fit.poliskyr.trainmeapp.networking.exception

import java.io.IOException

class ForbiddenException(override val message: String = "API: Forbidden request") : IOException()