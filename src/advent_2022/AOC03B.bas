 10 T0=TIME
 20 F$="AOC03I"
 30 ASSIGN# 1 TO F$
 40 DIM X$[60]
 50 DIM S(52)
 60 Z=0
 70 C=1
 80 DEF FNP(Q$)
 90 Q=NUM(Q$)-96
100 IF Q<0 THEN Q=Q+58
110 FNP=Q
120 FN END
130 FOR I=1 TO 52 @ S(I)=0 @ NEXT I
140 ON ERROR GOTO 310
150 READ# 1 ; X$
160 OFF ERROR
170 DISP X$
180 FOR I=1 TO LEN(X$)
190 T=FNP(X$[I,I])
200 S(T)=BINIOR(S(T),C)
210 NEXT I
220 C=C*2
230 IF C<8 THEN GOTO 140
240 C=1
241 P=0
250 FOR I=1 TO 52
260 IF S(I)=7 THEN P=I @ GOTO 280
270 NEXT I
280 DISP P
290 Z=Z+P
300 GOTO 130
310 ASSIGN# 1 TO *
320 DISP Z
330 DISP TIME-T0;"s"
340 END
