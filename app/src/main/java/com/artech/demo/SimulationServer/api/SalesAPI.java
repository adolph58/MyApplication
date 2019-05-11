package com.artech.demo.SimulationServer.api;

import com.artech.demo.SimulationServer.SQLiteUtils;
import com.artech.demo.entity.Custom;
import com.artech.demo.entity.MyLocation;
import com.artech.demo.util.DistanceByLocation;

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
