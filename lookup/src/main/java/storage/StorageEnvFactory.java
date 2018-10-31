package storage;

import org.lmdbjava.Env;
import org.lmdbjava.EnvFlags;
import web.configuration.LookupConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.nio.ByteBuffer;

@Singleton
public class StorageEnvFactory {

    private final String storagePath;
    private LookupConfiguration configuration;
    private Env<ByteBuffer> environment = null;

    @Inject
    public StorageEnvFactory(LookupConfiguration configurarion) {
        this.configuration = configurarion;
        this.storagePath = configurarion.getStorage();
    }

    public Env<ByteBuffer> getEnv() {

        if (environment != null) {
            return environment;
        }

        File thePath = new File(this.storagePath);
        if (!thePath.exists()) {
            thePath.mkdirs();
        }

        this.environment = Env.create()
                .setMapSize(100L * 1024L * 1024L * 1024L)
                .setMaxReaders(126)
                .setMaxDbs(3)
                .open(thePath, EnvFlags.MDB_NOTLS);

        return environment;

    }
}