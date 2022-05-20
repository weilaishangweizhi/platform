package com.hollysmart.platformsdk.interfaces;
import com.hollysmart.platformsdk.data.AppItem;

public class JsxInterface {
    public interface PlatformAppItemIF {
        void onItem(AppItem appItem);
    }
    public interface PlatFormCommonItemIF {
        void onItem(AppItem appItem);

        void onMore();
    }
    public interface PlatformAddOrRemove {
        void onAdd(AppItem appItem);

        void onRemove(AppItem appItem);
    }
}






















