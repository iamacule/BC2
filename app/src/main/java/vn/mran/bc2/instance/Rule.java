package vn.mran.bc2.instance;

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;
import java.util.Random;

import vn.mran.bc2.constant.PrefValue;
import vn.mran.bc2.helper.Log;
import vn.mran.bc2.util.Preferences;

import static vn.mran.bc2.util.ScreenUtil.getRandomNumber;

/**
 * Created by Mr An on 20/12/2017.
 */

public class Rule {

    private final String TAG = getClass().getSimpleName();

    public final String STATUS_ON = "on";

    private static Rule instance;

    private Preferences preferences;

    private int ruleChildAdditionalNumber;
    private int ruleChildAssignNum1;
    private int ruleChildAssignNum2;
    private int ruleChildAssignNum3;
    private int ruleChildAssignNum4;
    private int ruleChildAssignNum5;
    private int ruleChildAssignNum6;

    public final byte RULE_NORMAL = 0;
    public final byte RULE_MAIN = 1;
    private byte currentRule = RULE_NORMAL;

    //Bau, cua
    public final int[] RULE_MAIN_GONE_1 = new int[]{0, 1};

    //Tom ca
    public final int[] RULE_MAIN_GONE_2 = new int[]{2, 3};

    //Ga, nai
    public final int[] RULE_MAIN_GONE_3 = new int[]{4, 5};

    private int[] ruleMainGoneArrays;

    private int[] resultArrays = new int[]{};

    private Rule(Context context) {
        preferences = new Preferences(context);
        initValue();
    }

    private void initValue() {
        ruleChildAdditionalNumber = 3;
        ruleChildAssignNum1 = 5;
        ruleChildAssignNum2 = 3;
        ruleChildAssignNum3 = 2;
        ruleChildAssignNum4 = 1;
        ruleChildAssignNum5 = 0;
        ruleChildAssignNum6 = 4;
    }

    public static void init(Context context) {
        instance = new Rule(context);
    }

    public static Rule getInstance() {
        return instance;
    }

    public void setCurrentRule(byte currentRule) {
        this.currentRule = currentRule;
    }

    public void setRuleMainGoneArrays(int[] array) {
        this.ruleMainGoneArrays = array;
    }

    /**
     * Get result in battle
     *
     * @return
     */
    public int[] getResult() {
        int[] returnArrays = getRandomNumberArrays();
        switch (currentRule) {
            case RULE_NORMAL:
                Log.d(TAG, "Rule 1");
                returnArrays = getRule1();
                break;

            case RULE_MAIN:
                Log.d(TAG, "Rule Main");
                returnArrays = getRuleMain();
                break;
        }


        resultArrays = ArrayUtils.addAll(resultArrays, returnArrays);
        return returnArrays;
    }

    /**
     * Rule Main
     *
     * @return
     */
    private int[] getRuleMain() {
        int[] resultArray = new int[3];
        for (int i = 0; i < resultArray.length; i++) {
            while (true) {
                int value = getRandomAnimalPosition();
                if (value != ruleMainGoneArrays[0] && value != ruleMainGoneArrays[1]) {
                    resultArray[i] = value;
                    break;
                }
            }
        }
        return resultArray;
    }

    /**
     * Rule 1
     *
     * @return
     */
    private int[] getRule1() {
        int tong = 0;

        int range = 3;
        if (resultArrays.length <= 3) {
            Log.d(TAG, "resultArrays length = 0");
            return getRandomNumberArrays();
        }
        for (int i = resultArrays.length - 1; i >= resultArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultArrays[i]);
            switch (resultArrays[i]) {
                case 0:
                    tong += ruleChildAssignNum1;
                    break;
                case 1:
                    tong += ruleChildAssignNum2;
                    break;
                case 2:
                    tong += ruleChildAssignNum3;
                    break;
                case 3:
                    tong += ruleChildAssignNum4;
                    break;
                case 4:
                    tong += ruleChildAssignNum5;
                    break;
                default:
                    tong += ruleChildAssignNum6;
                    break;
            }
        }

        tong += ruleChildAdditionalNumber;
        Log.d(TAG, "Tong : " + tong);
        int number = tong % 6;
        Log.d(TAG, "Number : " + number);

        int[] returnArrays = getRandomNumberArrays();
        returnArrays[getRandomNumber(0, 2)] = number;
        return returnArrays;
    }

    private int[] getRandomNumberArrays() {
        Random random = new Random();
        return new int[]{random.nextInt((5 - 0) + 1),
                random.nextInt((5 - 0) + 1),
                random.nextInt((5 - 0) + 1)};
    }

    private int getRandomAnimalPosition() {
        return new Random().nextInt((5 - 0) + 1);
    }
}
