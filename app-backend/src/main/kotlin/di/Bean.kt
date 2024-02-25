package di

interface Bean

interface Bean0<R> : Bean {
    operator fun invoke(): R
}

interface SuspendBean0<R> : Bean {
    suspend operator fun invoke(): R
}

interface Bean1<P, R> : Bean {
    operator fun invoke(p: P): R
}

interface SuspendBean1<P, R> : Bean {
    suspend operator fun invoke(p: P): R
}

interface Bean2<P1, P2, R> : Bean {
    operator fun invoke(p1: P1, p2: P2): R
}

interface SuspendBean2<P1, P2, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2): R
}

interface Bean3<P1, P2, P3, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3): R
}

interface SuspendBean3<P1, P2, P3, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3): R
}

interface Bean4<P1, P2, P3, P4, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): R
}

interface SuspendBean4<P1, P2, P3, P4, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): R
}

interface Bean5<P1, P2, P3, P4, P5, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5): R
}

interface SuspendBean5<P1, P2, P3, P4, P5, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5): R
}

interface Bean6<P1, P2, P3, P4, P5, P6, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6): R
}

interface SuspendBean6<P1, P2, P3, P4, P5, P6, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6): R
}

interface Bean7<P1, P2, P3, P4, P5, P6, P7, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7): R
}

interface SuspendBean7<P1, P2, P3, P4, P5, P6, P7, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7): R
}

interface Bean8<P1, P2, P3, P4, P5, P6, P7, P8, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8): R
}

interface SuspendBean8<P1, P2, P3, P4, P5, P6, P7, P8, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8): R
}

interface Bean9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9): R
}

interface SuspendBean9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9): R
}

interface Bean10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> : Bean {
    operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10): R
}

interface SuspendBean10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> : Bean {
    suspend operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10): R
}


interface Bean11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> : Bean {
    operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
    ): R
}

interface SuspendBean11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> : Bean {
    suspend operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
    ): R
}

interface Bean12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> : Bean {
    operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
        p12: P12,
    ): R
}

interface SuspendBean12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> : Bean {
    suspend operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
        p12: P12,
    ): R
}

interface Bean13<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, R> : Bean {
    operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
        p12: P12,
        p13: P13,
    ): R
}

interface SuspendBean13<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, R> : Bean {
    suspend operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
        p12: P12,
        p13: P13,
    ): R
}

interface Bean14<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, R> : Bean {
    operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
        p12: P12,
        p13: P13,
        p14: P14,
    ): R
}


interface SuspendBean14<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, R> : Bean {
    suspend operator fun invoke(
        p1: P1,
        p2: P2,
        p3: P3,
        p4: P4,
        p5: P5,
        p6: P6,
        p7: P7,
        p8: P8,
        p9: P9,
        p10: P10,
        p11: P11,
        p12: P12,
        p13: P13,
        p14: P14,
    ): R
}

