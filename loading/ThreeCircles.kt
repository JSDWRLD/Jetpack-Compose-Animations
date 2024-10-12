@Composable
fun ThreeCirclesLoading(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = Color(0xFFAAC7FF),
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val lastCircle = circleValues.size - 1

    Box(
        modifier = modifier.fillMaxSize().shimmerEffect(), // Fill the parent container
        contentAlignment = Alignment.Center // Center the Row inside the Box
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center) // Center the Row horizontally
        ) {
            circleValues.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .graphicsLayer { translationY = -value * distance }
                        .background(color = circleColor, shape = CircleShape)
                )
                if (index != lastCircle) Spacer(modifier = Modifier.width(spaceBetween))
            }
        }
    }
}
