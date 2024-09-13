@Composable
fun FuturisticLoading() {
    var rotateOuter by remember {
        mutableStateOf(false)
    }


    val angle by animateFloatAsState(
        targetValue = if (rotateOuter) 360 * 3f else 0f,
        animationSpec = spring(
            visibilityThreshold = 0.3f,
            dampingRatio = 0.1f,
            stiffness = 0.87f
        )
    )
    val infiniteTransition = rememberInfiniteTransition()
    val scaleBox by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val centerOffset = with(LocalDensity.current) {
        Offset(
            LocalConfiguration.current.screenWidthDp.div(2).dp.toPx(),
            LocalConfiguration.current.screenHeightDp.div(2).dp.toPx()
        )
    }

    LaunchedEffect(key1 = true, block = {
        rotateOuter = !rotateOuter
        while (true){
            delay(2000)
            rotateOuter = !rotateOuter
        }
    })


    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.2f)
                .background(color = Color.Transparent)
                .shimmerEffect()
        ) {
            Box(
                Modifier
                    .scale(scaleBox)
                    .align(Alignment.Center)
            ) {
                // center circle
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                        .background(Color(0xFFAAC7FF), shape = CircleShape)
                )
                // two arc's
                Box(Modifier.rotate(angle)) {
                    Canvas(modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp), onDraw = {
                        drawArc(
                            color = Color(0xFFAAC7FF),
                            style = Stroke(
                                width = 3f,
                                cap = StrokeCap.Round,
                                join =
                                StrokeJoin.Round,
                            ),
                            startAngle = 180f,
                            sweepAngle = 288f,
                            useCenter = false
                        )

                    })
                }

                Box(Modifier.rotate(angle)) {
                    Canvas(modifier = Modifier
                        .rotate(180f)
                        .align(Alignment.Center)
                        .size(100.dp), onDraw = {
                        drawArc(
                            color = Color(0xFFD6E3FF),
                            style = Stroke(
                                width = 3f,
                                cap = StrokeCap.Round,
                                join =
                                StrokeJoin.Round,
                            ),
                            startAngle = 180f,
                            sweepAngle = 288f,
                            useCenter = false
                        )
                    }
                    )
                }

            }

        }
    }
}
