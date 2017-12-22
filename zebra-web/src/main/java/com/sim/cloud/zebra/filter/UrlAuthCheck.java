package com.sim.cloud.zebra.filter;

import java.util.ArrayList;

/**
 * 检查URL是否被忽略
 * 
 * @author Leo
 * @time 2017-04-28 15:51:16
 *
 */
public class UrlAuthCheck  {
	private ArrayList<String> actions = null;

	public void setPaths(String ignore) {
		if ((ignore == null) || (ignore.length() == 0)) {
			return;
		}
		if (this.actions == null)
			this.actions = new ArrayList<String>();
		else
			this.actions.clear();
		String[] actionArr = ignore.toString().split(",");
		for (String action : actionArr)
			this.actions.add(action);
	}

	public boolean check(String arg) {
	    if ((arg == null) || (arg.length() == 0)) {
            return false;
        }
        for (String a : this.actions) {
            if (arg.indexOf(a) >= 0) {
                return true;
            }
        }
        return false;
	}
}
