/**
 * This work is licensed under the Creative Commons Attribution 3.0 Unported 
 * License. To view a copy of this license, visit 
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative 
 * Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */

/* StackOverflow reference:
 * http://stackoverflow.com/questions/...
 */

#include <QMainWindow>
#include <QGraphicsView>
#include <QGraphicsItem>
#include <QVBoxLayout>
#include <QDesktopWidget>
#include <QApplication>
#include <QDebug>

/*
class GraphicsItem : public QGraphicsRectItem {
public:
    GraphicsItem ( qreal x, qreal y, qreal width, qreal height, QGraphicsItem * parent = 0 );

protected:
    void paint ( QPainter * painter, const QStyleOptionGraphicsItem * option, QWidget * widget);
};

class GraphicsScene : public QGraphicsScene {
};
*/

class ScaleWidget;
class ScaleEdgeWidget;
class Interactor;
class EditableItem;

class GraphicsSheet : public QGraphicsView {
    Q_OBJECT;

    QStringList sizeNames;
    QList<QSizeF> sizeDimensions;

    QStringList scaleNames;
    QList<float> scaleLevels;

    QStringList zoomNames;
    QList<float> zoomLevels;

    ScaleWidget* xScale;
    ScaleWidget* yScale;
    ScaleEdgeWidget* edge;

    Interactor* currentInteractor;
    //QColor viewColor;

    float xDpi;
    float yDpi;

    float drawScale;    // e.g. 1:2 => 0.5
    float zoomScale;    // e.g. 50% => 0.5
    QSizeF sceneSize;   // e.g. 148x210
    bool landscape;     // true or false (landscape or portrait)

    void updateSize();

public:
    GraphicsSheet(QWidget* parent);

    void addSize(const QString& name, const QSizeF& size);

    QStringList getSizeNames() const;

    void addZoom(const QString& name, float level);

    QStringList getZoomNames() const;

    void addScale(const QString& name, float scale);

    QStringList getScaleNames() const;

    void setUnit(const QString& unit);

    void setScale(float scale);

    void setZoom(float zoom);

    void setSize(const QSizeF& dimension);

    //void setColor(const QColor& color);

    //QColor getColor();

    void setScaleBackground(const QColor& color);

    void setInteractor(Interactor* interactor);

    Interactor* getInteractor();

    EditableItem* getFocusItem() const;

public slots:
    void setScale(int idx);

    void setZoom(int idx);

    void setSize(int idx);

    void setDirection(int idx);

private slots:
    void areaMoved();

protected:
    // @Override
    virtual void drawBackground(QPainter * painter, const QRectF & rect);

    // @Override
    virtual void drawForeground ( QPainter * painter, const QRectF & rect );

    void resizeEvent ( QResizeEvent * event );

    QSize sizeHint() const;

    void mousePressEvent ( QMouseEvent * event );

    void mouseMoveEvent ( QMouseEvent * event );

    void mouseReleaseEvent ( QMouseEvent * event );

    void wheelEvent(QWheelEvent * event );

    void paste();
};


class MainWindow : public QMainWindow {
    Q_OBJECT;

    QAction *actionX;
    GraphicsSheet* graphicsSheet;
    QMenuBar *menubar;
    QStatusBar *statusbar;
    QToolBar *toolBar;

public:
    MainWindow(QWidget* parent);

    ~MainWindow();

public slots:
	void printInfo();
};
