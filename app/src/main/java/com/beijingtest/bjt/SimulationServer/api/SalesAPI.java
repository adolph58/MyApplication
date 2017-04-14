package com.beijingtest.bjt.SimulationServer.api;

import com.beijingtest.bjt.SimulationServer.SQLiteUtils;
import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.entity.MyLocation;
import com.beijingtest.bjt.util.DistanceByLocation;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dev on 2017/3/30.
 */

public class SalesAPI {
    public static List<Custom> getCustomListByLocation(List<Custom> customList, MyLocation myLocation) {
        Iterator<Custom> it = customList.iterator();
        while(it.hasNext()) {
            Custom custom = it.next();
            if(custom.getLongitude() > 0 && myLocation.getLongitude() > 0) {
					double distance = DistanceByLocation.getDistance(myLocation.getLongitude(),myLocation.getLatitude(), custom.getLongitude(), custom.getLatitude());
					System.out.println("距离："+ distance);
					if (distance > 1) {
						it.remove();
					}
				}
        }
        return customList;
    }

    public static List<Custom> getCustomList() {
        SQLiteUtils sqLiteUtils = new SQLiteUtils();
        List<Custom> customList = sqLiteUtils.queryCustomList();
        return customList;
    }
}
