package com.sharonaapp.sharona.helper;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;

import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.network.UploadClothesInfoPartRequest;
import com.sharonaapp.sharona.utility.DialogHelper;

public class AddClothesHelper {

    public static boolean generalDetailsValidation(Activity activity)
    {
        if (!isClothesTypeValid())
        {
            DialogHelper.warnDialog(activity, "Invalid input", "Type is invalid!");
            return false;
        }

        if (!isClothesSizeValid())
        {
            DialogHelper.warnDialog(activity, "Invalid input", "Size is invalid!");
            return false;
        }
        if (!isClothesBrandValid())
        {
            DialogHelper.warnDialog(activity, "Invalid input", "Brand is invalid!");
            return false;
        }
        if (!isClothesColorValid())
        {
            DialogHelper.warnDialog(activity, "Invalid input", "Color is invalid!");
            return false;
        }
        if (!isClothesGenderValid())
        {
            DialogHelper.warnDialog(activity, "Invalid input", "Gender is invalid!");
            return false;
        }
        if (!isClothesUsedStatusValid())
        {
            DialogHelper.warnDialog(activity, "Invalid input", "Usage status is invalid!");
            return false;
        }

        return true;
    }

    public static boolean isClothesTypeValid()
    {
        return AddClothesDTO.toBeUploadedClothesType != null &&
                AddClothesDTO.toBeUploadedClothesType.length() > 0 &&
                MyApplication.getConfig().getWearingTypesNamesArrayList().contains(AddClothesDTO.toBeUploadedClothesType);
    }

    public static boolean isClothesSizeValid()
    {
        if (toBeUploadedClothesTypeIsShoes())
        {
            return AddClothesDTO.toBeUploadedClothesSize != null &&
                    AddClothesDTO.toBeUploadedClothesSize.length() > 0 &&
                    MyApplication.getConfig().getFootwearSizeArrayList().contains(AddClothesDTO.toBeUploadedClothesSize);
        }
        else
        {
            return AddClothesDTO.toBeUploadedClothesSize != null &&
                    AddClothesDTO.toBeUploadedClothesSize.length() > 0 &&
                    MyApplication.getConfig().getClothesSizeArrayList().contains(AddClothesDTO.toBeUploadedClothesSize);
        }
    }

    public static boolean isClothesBrandValid()
    {
        return AddClothesDTO.toBeUploadedClothesBrand != null &&
                AddClothesDTO.toBeUploadedClothesBrand.length() > 0 &&
                MyApplication.getConfig().getClothesBrandsArrayList().contains(AddClothesDTO.toBeUploadedClothesBrand);
    }

    public static boolean isClothesColorValid()
    {
        return AddClothesDTO.toBeUploadedClothesColor != null &&
                AddClothesDTO.toBeUploadedClothesColor.length() > 0 &&
                MyApplication.getConfig().getColorArrayList().contains(AddClothesDTO.toBeUploadedClothesColor);
    }

    public static boolean isClothesGenderValid()
    {
        return AddClothesDTO.toBeUploadedClothesGender != null &&
                AddClothesDTO.toBeUploadedClothesGender.length() > 0 &&
                MyApplication.getConfig().getClothesGenderArrayList().contains(AddClothesDTO.toBeUploadedClothesGender);
    }

    public static boolean isClothesUsedStatusValid()
    {
        return AddClothesDTO.toBeUploadedClothesUsedStatus != null &&
                AddClothesDTO.toBeUploadedClothesUsedStatus.length() > 0 &&
                MyApplication.getConfig().getUsedStatusArrayList().contains(AddClothesDTO.toBeUploadedClothesUsedStatus);
    }


    public static boolean toBeUploadedClothesTypeIsShoes()
    {
        return AddClothesDTO.toBeUploadedClothesType != null &&
                AddClothesDTO.toBeUploadedClothesType.length() > 0 &&
                AddClothesDTO.toBeUploadedClothesType.equalsIgnoreCase("Shoes");
    }

    public static boolean pricingDetailsValidation(Activity activity)
    {
        if (isClothesBothForSellAndRent())
        {
            if (!isClothesBuyPriceValid())
            {
                DialogHelper.warnDialog(activity, "Invalid input", "Buy Price is invalid!");
                return false;
            }

            if (!isClothesRentPriceValid())
            {
                DialogHelper.warnDialog(activity, "Invalid input", "Rent Price is invalid!");
                return false;
            }

            if (!isClothesBuyPriceGreaterThanRentalPrice())
            {
                DialogHelper.warnDialog(activity, "Invalid input", "Buy price must be more than Rent price!");
                return false;
            }

            return true;

        }

        if (isClothesOnlyForSell())
        {
            if (!isClothesBuyPriceValid())
            {
                DialogHelper.warnDialog(activity, "Invalid input", "Buy Price is invalid!");
                return false;
            }

            return true;
        }

        if (isClothesOnlyForRent())
        {
            if (!isClothesRentPriceValid())
            {
                DialogHelper.warnDialog(activity, "Invalid input", "Rent Price is invalid!");
                return false;
            }

            return true;
        }

        return false;

    }

    private static boolean isClothesBothForSellAndRent()
    {
        return AddClothesDTO.clothesIsSellable && AddClothesDTO.clothesIsRentable;
    }

    private static boolean isClothesOnlyForSell()
    {
        return AddClothesDTO.clothesIsSellable && !AddClothesDTO.clothesIsRentable;
    }

    private static boolean isClothesOnlyForRent()
    {
        return !AddClothesDTO.clothesIsSellable && AddClothesDTO.clothesIsRentable;
    }

    public static boolean isClothesBuyPriceValid()
    {
        if (AddClothesDTO.buyPrice != null)
        {
            try
            {
                Integer buyPrice = Integer.valueOf(AddClothesDTO.buyPrice);

                return true;
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public static boolean isClothesRentPriceValid()
    {
        if (AddClothesDTO.rentPrice != null)
        {
            try
            {
                Integer rentPrice = Integer.valueOf(AddClothesDTO.rentPrice);

                return true;
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public static boolean isClothesBuyPriceGreaterThanRentalPrice()
    {
        if (AddClothesDTO.buyPrice != null &&
                AddClothesDTO.rentPrice != null)
        {
            try
            {
                Integer buyPrice = Integer.valueOf(AddClothesDTO.buyPrice);
                Integer rentPrice = Integer.valueOf(AddClothesDTO.rentPrice);

                if (buyPrice > rentPrice)
                {
                    return true;
                }
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public static void saveDescription(TextInputEditText descriptionTextInputEditText)
    {
        if (descriptionTextInputEditText != null &&
                descriptionTextInputEditText.getText() != null &&
                descriptionTextInputEditText.getText().toString().length() > 0)
        {
            AddClothesDTO.toBeUploadedClothesDescription = descriptionTextInputEditText.getText().toString();
        }
        else
        {
            AddClothesDTO.toBeUploadedClothesDescription = "No description entered by user";
        }
    }

    public static boolean swapDetailsValidation(Activity activity)
    {
        if (!AddClothesDTO.clothesIsSwapable)
        {
            return true;
        }

//        if (!isClothesSwapConditionTypeValid())
//        {
//            DialogHelper.warnDialog(activity, "Invalid input", "Type is invalid!");
//            return false;
//        }
//
//        if (!isClothesSwapConditionSizeValid())
//        {
//            DialogHelper.warnDialog(activity, "Invalid input", "Size is invalid!");
//            return false;
//        }
//        if (!isClothesSwapConditionBrandValid())
//        {
//            DialogHelper.warnDialog(activity, "Invalid input", "Brand is invalid!");
//            return false;
//        }
//        if (!isClothesSwapConditionColorValid())
//        {
//            DialogHelper.warnDialog(activity, "Invalid input", "Color is invalid!");
//            return false;
//        }
        return true;
    }

    public static boolean isClothesSwapConditionTypeValid()
    {
        return AddClothesDTO.toBeUploadedClothesSwapConditionType != null &&
                AddClothesDTO.toBeUploadedClothesSwapConditionType.length() > 0 &&
                MyApplication.getConfig().getWearingTypesNamesArrayList().contains(AddClothesDTO.toBeUploadedClothesSwapConditionType);
    }

    public static boolean isClothesSwapConditionSizeValid()
    {
        if (toBeUploadedClothesTypeIsShoes())
        {
            return AddClothesDTO.toBeUploadedClothesSize != null &&
                    AddClothesDTO.toBeUploadedClothesSize.length() > 0 &&
                    MyApplication.getConfig().getFootwearSizeArrayList().contains(AddClothesDTO.toBeUploadedClothesSize);
        }
        else
        {
            return AddClothesDTO.toBeUploadedClothesSwapConditionSize != null &&
                    AddClothesDTO.toBeUploadedClothesSwapConditionSize.length() > 0 &&
                    MyApplication.getConfig().getClothesSizeArrayList().contains(AddClothesDTO.toBeUploadedClothesSwapConditionSize);
        }
    }

    public static boolean isClothesSwapConditionBrandValid()
    {
        return AddClothesDTO.toBeUploadedClothesSwapConditionBrand != null &&
                AddClothesDTO.toBeUploadedClothesSwapConditionBrand.length() > 0 &&
                MyApplication.getConfig().getClothesBrandsArrayList().contains(AddClothesDTO.toBeUploadedClothesSwapConditionBrand);
    }

    public static boolean isClothesSwapConditionColorValid()
    {
        return AddClothesDTO.toBeUploadedClothesSwapConditionColor != null &&
                AddClothesDTO.toBeUploadedClothesSwapConditionColor.length() > 0 &&
                MyApplication.getConfig().getColorArrayList().contains(AddClothesDTO.toBeUploadedClothesSwapConditionColor);
    }


    public static boolean imageUploadValidation(Activity activity)
    {
        if (AddClothesDTO.numberOfUploadedImages > 1)
        {
            return true;
        }
        else
        {
            DialogHelper.warnDialog(activity, "Image is needed", "Upload at least 2 images to finish the process");
            return false;
        }
    }

    public static void reset()
    {
        AddClothesDTO.currentState = 0;
        AddClothesDTO.toBeUploadedClothesType = null;
        AddClothesDTO.toBeUploadedClothesSize = null;
        AddClothesDTO.toBeUploadedClothesBrand = null;
        AddClothesDTO.toBeUploadedClothesColor = null;
        AddClothesDTO.toBeUploadedClothesGender = null;
        AddClothesDTO.toBeUploadedClothesUsedStatus = null;

        AddClothesDTO.clothesIsSellable = false;
        AddClothesDTO.clothesIsRentable = false;
        AddClothesDTO.buyPrice = null;
        AddClothesDTO.rentPrice = null;
        AddClothesDTO.toBeUploadedClothesDescription = null;

        AddClothesDTO.clothesIsSwapable = false;
        AddClothesDTO.toBeUploadedClothesSwapConditionType = null;
        AddClothesDTO.toBeUploadedClothesSwapConditionSize = null;
        AddClothesDTO.toBeUploadedClothesSwapConditionBrand = null;
        AddClothesDTO.toBeUploadedClothesSwapConditionColor = null;

        AddClothesDTO.numberOfUploadedImages = 0;

    }


    public static class AddClothesDTO {

        public static int currentState = 0;

        public static String toBeUploadedClothesType;
        public static String toBeUploadedClothesSize;
        public static String toBeUploadedClothesBrand;
        public static String toBeUploadedClothesColor;
        public static String toBeUploadedClothesGender;
        public static String toBeUploadedClothesUsedStatus;


        public static boolean clothesIsSellable = true;
        public static boolean clothesIsRentable = true;
        public static String buyPrice;
        public static String rentPrice;
        public static String toBeUploadedClothesDescription;


        public static boolean clothesIsSwapable = false;
        public static String toBeUploadedClothesSwapConditionType;
        public static String toBeUploadedClothesSwapConditionSize;
        public static String toBeUploadedClothesSwapConditionBrand;
        public static String toBeUploadedClothesSwapConditionColor;


        public static int numberOfUploadedImages;


        public static UploadClothesInfoPartRequest getUploadClothesInfoPartRequest()
        {
            UploadClothesInfoPartRequest uploadClothesInfoPartRequest = new UploadClothesInfoPartRequest();

            uploadClothesInfoPartRequest.setTitle(toBeUploadedClothesColor + " " + toBeUploadedClothesType);
            uploadClothesInfoPartRequest.setType(toBeUploadedClothesType);
            uploadClothesInfoPartRequest.setSize(toBeUploadedClothesSize);
            uploadClothesInfoPartRequest.setBrand(toBeUploadedClothesBrand);
            uploadClothesInfoPartRequest.setColor(toBeUploadedClothesColor);
            uploadClothesInfoPartRequest.setGender(toBeUploadedClothesGender);
            uploadClothesInfoPartRequest.setNewOrUsedStatus(toBeUploadedClothesUsedStatus);

            uploadClothesInfoPartRequest.setSellable(clothesIsSellable);
            if (clothesIsSellable)
            {
                uploadClothesInfoPartRequest.setBuyPrice(Integer.valueOf(buyPrice));
            }
            uploadClothesInfoPartRequest.setLendable(clothesIsRentable);
            if (clothesIsRentable)
            {
                uploadClothesInfoPartRequest.setRentPrice(Integer.valueOf(rentPrice));
            }

            uploadClothesInfoPartRequest.setDescription(toBeUploadedClothesDescription);

            if (clothesIsSwapable)
            {
                UploadClothesInfoPartRequest.SwapCondition swapCondition = uploadClothesInfoPartRequest.new SwapCondition();

                if (toBeUploadedClothesSwapConditionType != null &&
                        toBeUploadedClothesSwapConditionType.length() > 0)
                {
                    swapCondition.setType(toBeUploadedClothesSwapConditionType);
                }
                if (toBeUploadedClothesSwapConditionSize != null &&
                        toBeUploadedClothesSwapConditionSize.length() > 0)
                {
                    swapCondition.setSize(toBeUploadedClothesSwapConditionSize);
                }
                if (toBeUploadedClothesSwapConditionBrand != null &&
                        toBeUploadedClothesSwapConditionBrand.length() > 0)
                {
                    swapCondition.setBrand(toBeUploadedClothesSwapConditionBrand);
                }
                if (toBeUploadedClothesSwapConditionColor != null &&
                        toBeUploadedClothesSwapConditionColor.length() > 0)
                {
                    swapCondition.setColor(toBeUploadedClothesSwapConditionColor);
                }


                uploadClothesInfoPartRequest.setSwapCondition(swapCondition);
            }

            return uploadClothesInfoPartRequest;
        }
    }
}
