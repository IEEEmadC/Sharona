package com.sharonaapp.sharona;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.activity.SplashActivity;

/**
 * The type Shortcut helper.
 */
public class ShortcutHelper {

    //region Constants
    private final String FAV_SHORTCUT_BASE_URL = "snapp://shortcut/here/";
    //endregion

    //region Fields
    private Context context;
    //endregion

    //region Constructors

    /**
     * Instantiates a new Shortcut helper.
     *
     * @param context the context
     */

    public ShortcutHelper(Context context)
    {
        this.context = context;

    }
    //endregion

    /**
     * Can create homescreen shortcut boolean.
     *
     * @return the boolean
     */
    //region Functions
    public static boolean canCreateHomescreenShortcut()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Create homescreen shortcut boolean.
     *
     * @param myModel the favorite model
     * @return the boolean
     */
    public boolean createHomescreenShortcut(MyModel myModel)
    {
        if (canCreateHomescreenShortcut())
        {
            StringBuilder sbFavShortcut = new StringBuilder(FAV_SHORTCUT_BASE_URL);
            sbFavShortcut.append(myModel.getFirstString()).append(",").append(myModel.getSecondString())
                    .append(",").append(myModel.getId());

            Intent shortcutIntent = new Intent();
            shortcutIntent.setAction(Intent.ACTION_VIEW);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent.setData(Uri.parse(sbFavShortcut.toString()));

            Intent addIntent = new Intent();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, " " + myModel.getName());
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), R.drawable.ic_add));

            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            context.getApplicationContext().sendBroadcast(addIntent);

            return true;

        }
        else
        {
            return false;
        }
    }

    /**
     * Remove homsescreen shortcut boolean.
     *
     * @param myModel the favorite model
     * @return the boolean
     */
    public boolean removeHomsescreenShortcut(MyModel myModel)
    {
        if (canCreateHomescreenShortcut())
        {

//
//            PackageManager pm = context.getPackageManager();
//            // Intent to Start activity
//            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
//            if (info != null)
//            {
//                Intent shortcutIntent = pm
//                        .getLaunchIntentForPackage(packageName);
//                if (shortcutIntent != null)
//                {
//                    final Intent removeIntent = new Intent();
//                    removeIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
//                            shortcutIntent);
//                    removeIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
//                            info.loadLabel(pm));
//                    removeIntent.putExtra("duplicate", false);
//
//                    removeIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
//                    context.sendBroadcast(removeIntent);
//                }
//            }


            StringBuilder sbFavShortcut = new StringBuilder(FAV_SHORTCUT_BASE_URL);
            sbFavShortcut.append(myModel.getFirstString()).append(",").append(myModel.getSecondString())
                    .append(",").append(myModel.getId());

//            Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage("cab.snapp.passenger");
            Intent shortcutIntent = new Intent(context.getApplicationContext(), SplashActivity.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent.setData(Uri.parse(sbFavShortcut.toString()));

            Intent removeIntent = new Intent();
            removeIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            removeIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, myModel.getName());
            removeIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), R.drawable.ic_add));
            removeIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
            context.sendBroadcast(removeIntent);

            return true;
        }
        else
        {
            return false;
        }
    }


    public void addShortcut()
    {
        StringBuilder sbFavShortcut = new StringBuilder(FAV_SHORTCUT_BASE_URL);
        sbFavShortcut.append("some random test text");

        Intent shortcutIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_VIEW);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.setData(Uri.parse(sbFavShortcut.toString()));


        // Adding shortcut for MainActivity
        // on Home screen
//        Intent shortcutIntent = new Intent(context.getApplicationContext(),
//                MainActivity.class);
//        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Shortcut Example");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(
                        context.getApplicationContext(), R.drawable.ic_close));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        context.getApplicationContext().sendBroadcast(addIntent);
    }

    public void removeShortcut()
    {
        // Deleting shortcut for MainActivity
        // on Home screen
        StringBuilder sbFavShortcut = new StringBuilder(FAV_SHORTCUT_BASE_URL);
        sbFavShortcut.append("some random test text");

        Intent shortcutIntent = new Intent(context.getApplicationContext(),
                MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_VIEW);
        shortcutIntent.setData(Uri.parse(sbFavShortcut.toString()));

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Shortcut Example");
        addIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        context.getApplicationContext().sendBroadcast(addIntent);
    }


//endregion

}
