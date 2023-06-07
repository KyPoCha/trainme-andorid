package cz.cvut.fit.poliskyr.trainmeapp.networking.exception

import java.io.IOException

class BadGatewayException(override val message: String = "API: Bad Request"): IOException()