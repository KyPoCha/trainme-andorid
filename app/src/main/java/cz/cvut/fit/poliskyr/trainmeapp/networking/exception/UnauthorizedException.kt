package cz.cvut.fit.poliskyr.trainmeapp.networking.exception

import java.io.IOException

class UnauthorizedException(override val message: String = "API: Unauthorized") : IOException()