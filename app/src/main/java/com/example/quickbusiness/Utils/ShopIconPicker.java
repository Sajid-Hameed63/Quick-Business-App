package com.example.quickbusiness.Utils;

import com.example.quickbusiness.R;

public class ShopIconPicker {
    static int[] icons = {
            R.drawable.ic_icon_1,
            R.drawable.ic_icon_2,
            R.drawable.ic_icon_3,
            R.drawable.ic_icon_4,
            R.drawable.ic_icon_5,
            R.drawable.ic_icon_6,
            R.drawable.ic_icon_7,
            R.drawable.ic_icon_8

    };
    static int currentIcon = 0;
    public static int getIcon(){
        currentIcon = (currentIcon + 1) % icons.length;
        return icons[currentIcon];
    }
}
