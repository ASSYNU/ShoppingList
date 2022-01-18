import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'colors.dart';

class ShoppingListTheme {
  static ThemeData light = ThemeData(
      scaffoldBackgroundColor: Colors.white,
      fontFamily: 'Roboto',
      colorScheme: const ColorScheme(
        primary: ThemeColor.light_primaryColor,
        secondary: ThemeColor.light_secondary,
        surface: ThemeColor.light_surface,
        background: ThemeColor.light_background,
        error: ThemeColor.light_error,
        // "On" Colors
        onPrimary: ThemeColor.light_onPrimaryColor,
        onSecondary: ThemeColor.light_onSecondary,
        onSurface: ThemeColor.light_primaryColor,
        onBackground: ThemeColor.light_onBackground,
        onError: ThemeColor.light_onError,
        brightness: Brightness.dark,
      ),
      appBarTheme: const AppBarTheme(
        backgroundColor: ThemeColor.light_background,
        foregroundColor: ThemeColor.light_onBackground,
        elevation: 0,
        systemOverlayStyle: SystemUiOverlayStyle(
            statusBarColor: ThemeColor.light_background, // Status bar
            statusBarIconBrightness: Brightness.dark,
            systemNavigationBarColor: Colors.redAccent),
      ));

  static ThemeData dark = ThemeData(
      scaffoldBackgroundColor: Colors.black,
      fontFamily: 'Roboto',
      colorScheme: const ColorScheme(
        primary: ThemeColor.dark_primaryColor,
        secondary: ThemeColor.dark_secondary,
        surface: ThemeColor.dark_surface,
        background: ThemeColor.dark_background,
        error: ThemeColor.dark_error,
        // "On" Colors
        onPrimary: ThemeColor.dark_onPrimaryColor,
        onSecondary: ThemeColor.dark_onSecondary,
        onSurface: ThemeColor.dark_primaryColor,
        onBackground: ThemeColor.dark_onBackground,
        onError: ThemeColor.dark_onError,
        brightness: Brightness.light,
      ),
      appBarTheme: const AppBarTheme(
        backgroundColor: ThemeColor.dark_background,
        foregroundColor: ThemeColor.dark_onBackground,
        systemOverlayStyle: SystemUiOverlayStyle(
            statusBarColor: ThemeColor.dark_background, // Status bar
            statusBarIconBrightness: Brightness.light),
      ));
}
