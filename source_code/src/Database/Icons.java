/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Blob;

/**
 *
 * @author kn
 */
public class Icons {
    private int icon_id;
    private Blob icon_image;

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public Blob getIcon_image() {
        return icon_image;
    }

    public void setIcon_image(Blob icon_image) {
        this.icon_image = icon_image;
    }
    
}
