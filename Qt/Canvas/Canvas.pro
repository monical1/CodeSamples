# This work is licensed under the Creative Commons Attribution 3.0 Unported
# License. To view a copy of this license, visit
# http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
# Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

TEMPLATE = app
QT = gui core widgets printsupport

CONFIG += qt warn_on
FORMS = Canvas.ui
HEADERS = Canvas.h MainWindow.h
SOURCES = Canvas.cpp MainWindow.cpp main.cpp
# LIBS += user32.dll
