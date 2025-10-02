package android.bootcamp.filmbox.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val MerriweatherSans = FontFamily(
    Font(android.bootcamp.filmbox.R.font.merriweather_sans_regular, FontWeight.Normal),
    Font(android.bootcamp.filmbox.R.font.merriweather_sans_bold, FontWeight.Bold)
)



// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily =MerriweatherSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Charcoal
    ),

    bodyMedium = TextStyle(
        fontFamily = MerriweatherSans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Charcoal
    ),

    titleLarge = TextStyle(
        fontFamily = MerriweatherSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Charcoal
    ),

    titleMedium = TextStyle(
        fontFamily = MerriweatherSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = Charcoal
    ),

    labelSmall = TextStyle(
        fontFamily = MerriweatherSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Indigo950
    ),

    labelMedium = TextStyle(
        fontFamily = MerriweatherSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        color = Indigo950
    ),

    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Indigo950
    )
)
