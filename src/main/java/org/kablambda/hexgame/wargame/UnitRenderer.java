package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexPixelCalculator;
import org.kablambda.hexgame.HexRenderer;
import org.kablambda.hexgame.UIParameters;
import org.locationtech.jts.math.Vector2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import static java.lang.StrictMath.PI;

public class UnitRenderer implements HexRenderer<Unit> {

    private final UIParameters uiParameters;
    private final SymbolFactory symbolFactory;
    private final HexPixelCalculator calculator;

    public UnitRenderer(UIParameters uiParameters, SymbolFactory symbolFactory, HexPixelCalculator calculator) {
        this.uiParameters = uiParameters;
        this.symbolFactory = symbolFactory;
        this.calculator = calculator;
    }

    @Override
    public void paint(Graphics2D g, Unit unit) {
        g.setColor(unit.getSelected() ? Color.BLACK : unit.getTeam().getColor());
        g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(symbolFactory.infantry(uiParameters.getHexSideLength()));
        Arc2D arc = new Arc2D.Double();
        arc.setArcByCenter(0, 0, uiParameters.getHexSideLength() * 0.7, unit.getFatigue() * 180, 180 - (unit.getFatigue() * 180), Arc2D.OPEN);
        g.setColor(unit.getTeam().getColor().darker());
        g.draw(arc);
        arc.setArcByCenter(0, 0, uiParameters.getHexSideLength() * 0.7, 0, -(unit.getSupply() * 180), Arc2D.OPEN);
        g.draw(arc);
        if (unit.getGoal() != null) {
            drawGoalArrow(g, unit.getLocation(), unit.getGoal(), unit.getTeam());
        }
        if (unit.getState() instanceof Unit.Fighting fighting && fighting.getAttacker().equals(unit)) {
            HexSide s = calculator.hexSideBetween(unit.getLocation(), fighting.getAttackersTargetHex());
            g.setColor(Color.ORANGE);
            g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            HexSide.Edge edge = s.edgeVertices(calculator);
            g.fill(symbolFactory.combat(uiParameters.getHexSideLength()));
        }
    }

    private void drawGoalArrow(Graphics2D g, HexAddress startAddress, HexAddress endAddress, Team team) {
        var start = calculator.center(startAddress);
        var end = calculator.center(endAddress);
        var vector = new Vector2D(end.getX() - start.getX(), end.getY() - start.getY());
        var basis = vector.normalize();
        var missing = basis.multiply(uiParameters.getHexSideLength()/1.65);
        var arrowArm = basis.multiply(uiParameters.getHexSideLength()/2);
        var drawEndV = vector.subtract(missing.multiply(0.8));
        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(team.getColor().getColorSpace(), team.getColor().getComponents(new float[4]), 0.33f));
        Path2D arrow = new Path2D.Double();
        moveTo(arrow, missing);
        lineTo(arrow, drawEndV);
        lineTo(arrow, drawEndV.add(arrowArm.rotate(-PI*0.75)));
        moveTo(arrow, drawEndV);
        lineTo(arrow, drawEndV.add(arrowArm.rotate(PI*0.75)));
        g.draw(arrow);
    }

    private Point2D from(Vector2D vector) {
        return new Point2D.Double(vector.getX(), vector.getY());
    }

    private void moveTo(Path2D path, Vector2D vector) {
        path.moveTo(vector.getX(), vector.getY());
    }
    private void lineTo(Path2D path, Vector2D vector) {
        path.lineTo(vector.getX(), vector.getY());
    }
}
