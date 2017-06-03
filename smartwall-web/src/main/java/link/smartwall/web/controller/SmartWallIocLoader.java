package link.smartwall.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.map.MapLoader;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class SmartWallIocLoader extends MapLoader {

    private static final Log log = Logs.get();

    public SmartWallIocLoader(Reader reader) {
        loadFromReader(reader);
        if (log.isDebugEnabled())
            log.debugf("Loaded %d bean define from reader --\n%s", getMap().size(), getMap().keySet());
    }

    public SmartWallIocLoader() {
        this.setMap(new HashMap<String, Map<String, Object>>());

        InputStream is = this.getClass().getResourceAsStream("/ioc/ioc.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        loadFromReader(br);

        if (log.isDebugEnabled())
            log.debugf("Loaded %d bean define from path=/ioc/ioc.json --> %s", getMap().size(), getMap().keySet());
    }

    private void loadFromReader(Reader reader) {
        String s = Lang.readAll(reader);

        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) Json.fromJson(s);

        if (null != map && map.size() > 0)
            getMap().putAll(map);
    }
}