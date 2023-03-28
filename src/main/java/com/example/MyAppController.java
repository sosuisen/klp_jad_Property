package com.example;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class MyAppController {

	@FXML
	private CheckBox creditCheckBox;

	@FXML
	private TextField creditField;

	@FXML
	private TextField inputFieldA;

	@FXML
	private TextField inputFieldB;

	@FXML
	private TextField resultField;

	@FXML
	private Slider slider;

	@FXML
	private TextField sliderField;

	public void initialize() {
		/**
		 * 計算
		 */
		// 2つの整数プロパティ（IntegerProperty）を用意
		IntegerProperty numA = new SimpleIntegerProperty(1);
        IntegerProperty numB = new SimpleIntegerProperty(2);
        // この2つの掛け算の結果を維持する、バインディング
        NumberBinding mul = numA.multiply(numB);
        System.out.println(mul.getValue()); // 現在のバインディングの値は2
        numA.set(2); //　片方のプロパティを変更
        System.out.println(mul.getValue()); // 現在のバインディングの値が4に変わる
        
        // 入力欄とプロパティ連動させる
        // 整数プロパティと文字プロパティでは型が異なるので、Converterを用いる必要がります。
        var converter = new NumberStringConverter();
        inputFieldA.textProperty().bindBidirectional(numA, converter);
        inputFieldB.textProperty().bindBidirectional(numB, converter);
        // NumberBindingの値は文字列に変換してバインドします。
        resultField.textProperty().bind(mul.asString());
		
		/**
		 * スライダー
		 */
		// 片方向。スライダーを変化させると、入力欄の値が変化します。
		// sliderField.textProperty().bind(slider.valueProperty().asString());
        
		// 双方向。スライダーまたは入力欄へ入力すると、もう片方も変化します。
		sliderField.textProperty().bindBidirectional(slider.valueProperty(), converter);
		// スライダは通常、小数点以下の目盛りも取得します。
		// 整数のみにしたい場合は、イベントハンドラで値を整数に変換してセットすればOK
		slider.valueProperty().addListener((obs, oldVal, newVal) ->
	    	slider.setValue(Math.round(newVal.doubleValue())));

		/**
		 * UIの有効化・無効化
		 */
		// creditCheckBoxの状態を示すBooleanPropertyとcreditFieldの無効化を連動させます。 
		// BooleanProperty は not()で true, false が反転します。
		creditField.disableProperty().bind(creditCheckBox.selectedProperty().not());
	}
}
