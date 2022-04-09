import cv2
import matplotlib.pyplot as plt
import numpy as np
import time
import random

vid = cv2.VideoCapture(0)

cTime = time.time()

def apply_hsv_fiplter(self, original_image, hsv_filter=None):
    # convert image to HSV
    hsv = cv2.cvtColor(original_image, cv2.COLOR_BGR2HSV)

    # if we haven't been given a defined filter, use the filter values from the GUI
    if not hsv_filter:
        hsv_filter = self.get_hsv_filter_from_controls()

    # add/subtract saturation and value
    h, s, v = cv2.split(hsv)
    s = self.shift_channel(s, hsv_filter.sAdd)
    s = self.shift_channel(s, -hsv_filter.sSub)
    v = self.shift_channel(v, hsv_filter.vAdd)
    v = self.shift_channel(v, -hsv_filter.vSub)
    hsv = cv2.merge([h, s, v])

    # Set minimum and maximum HSV values to display
    lower = np.array([hsv_filter.hMin, hsv_filter.sMin, hsv_filter.vMin])
    upper = np.array([hsv_filter.hMax, hsv_filter.sMax, hsv_filter.vMax])
    # Apply the thresholds
    mask = cv2.inRange(hsv, lower, upper)
    result = cv2.bitwise_and(hsv, hsv, mask=mask)

    # convert back to BGR for imshow() to display it properly
    img = cv2.cvtColor(result, cv2.COLOR_HSV2BGR)

    return img



while(True):
    ret, frame=vid.read()
    ret, thresh1 = cv2.threshold(frame, 120, 255, cv2.THRESH_BINARY)
    ret, thresh2 = cv2.threshold(frame, 120, 255, cv2.THRESH_BINARY_INV)
    ret, thresh3 = cv2.threshold(frame, 120, 255, cv2.THRESH_TRUNC)
    ret, thresh4 = cv2.threshold(frame, 120, 255, cv2.THRESH_TOZERO)
    ret, thresh5 = cv2.threshold(frame, 120, 255, cv2.THRESH_TOZERO_INV)

    b, g, r = cv2.split(frame)
    r = b

    out = cv2.addWeighted( b, 1, b, 0, 0)

    circles = cv2.HoughCircles(out, cv2.HOUGH_GRADIENT, 1.2, 100)
    if circles is not None:
        circles = np.round(circles[0, :]).astype("int")
        for (x, y, r) in circles:
            cv2.circle(out, (x, y), r, (0, 255, 0), 4)
    #cv2.imshow('frame', apply_hsv_filter(frame))
    frame = apply_hsv_filter(frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
            break
vid.release()

cv2.destroyAllWindows()