package io.github.scottmaclure.character.traits.asyn;

import java.io.IOException;
import java.net.ConnectException;

/**
 * @author jacek on 8/2/14 13:31
 * @origin io.github.scottmaclure.character.traits.network.api.asyn
 */
public class AsynException extends Throwable {
    private Type type;

    public AsynException(Throwable cause) {
        super(cause);
        if (cause instanceof IOException) {
            type = Type.IO;
        } else if (cause instanceof ConnectException) {
            type = Type.NETWORK;
        } else if (cause instanceof NullPointerException) {
            type = Type.NPE;
        }
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        IO, NETWORK, NPE
    }

}
