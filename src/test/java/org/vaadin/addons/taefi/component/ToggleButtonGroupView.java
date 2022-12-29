package org.vaadin.addons.taefi.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.*;
import java.util.stream.Collectors;

@Route("")
@CssImport("./styles/demo-view-styles.css")
public class ToggleButtonGroupView extends VerticalLayout {

    enum Status {
        NOT_PROCESSED, DECLINED, APPROVED
    }

    enum Menu {
        PASTA, PIZZA, BURGER, SALAD
    }

    enum Direction {
        LEFT, RIGHT, UP, DOWN, FORWARD, BACKWARD
    }

    enum Answer {
        YES, NO
    }

    static class Desert {
        String name;
        int availableCount;

        public Desert(String name, int availableCount) {
            this.name = name;
            this.availableCount = availableCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Desert desert = (Desert) o;
            return name.equals(desert.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public ToggleButtonGroupView() {

        ToggleButtonGroup<Menu> group1 = new ToggleButtonGroup<>(List.of(Menu.values()));
        group1.setLabel("Menu: [toggleable = false] (ValueChangeListener publishes the selected value)");
        group1.setToggleable(false);
        Label lbl1 = new Label("Selected: ");
        Label selected1 = new Label();
        group1.addValueChangeListener(event -> selected1.setText(Optional.ofNullable(event.getValue()).map(Objects::toString).orElse("")));
        Button clear1 = new Button("Clear Selection", event -> group1.setValue(null));

        ToggleButtonGroup<Status> group2 = new ToggleButtonGroup<>();
        group2.setLabel("Status: [width-full] (Hover the mouse to see the tooltip)");
        group2.setTooltipText(String.format("Labels for original values of %s generated based on the configured itemLabelGenerator.",
                Arrays.stream(Status.values()).map(Object::toString).collect(Collectors.joining(", "))));
        group2.setItemLabelGenerator(status -> switch (status) {
            case NOT_PROCESSED -> "Not processed";
            case APPROVED -> "Approved";
            case DECLINED -> "Declined";
        });
        group2.setItems(Status.values());
        group2.setWidthFull();

        ToggleButtonGroup<Direction> group3 = new ToggleButtonGroup<>();
        group3.setLabel("Direction: (icons are generated based on itemIconGenerator property)");
        group3.setItemIconGenerator(direction -> switch (direction) {
            case LEFT -> VaadinIcon.ARROW_CIRCLE_LEFT.create();
            case RIGHT -> VaadinIcon.ARROW_CIRCLE_RIGHT.create();
            case UP -> VaadinIcon.ARROW_CIRCLE_UP.create();
            case DOWN -> VaadinIcon.ARROW_CIRCLE_DOWN.create();
            case FORWARD -> VaadinIcon.ARROW_FORWARD.create();
            case BACKWARD -> VaadinIcon.ARROW_BACKWARD.create();
        });
        group3.setItems(Direction.values());

        ToggleButtonGroup<Answer> group4 = new ToggleButtonGroup<>();
        group4.setItems(List.of(Answer.values()));
        group4.setValue(Answer.YES);
        HorizontalLayout line4 = new HorizontalLayout(group4, new Span("(No label. Default value is set by calling setValue API)"));
        line4.setAlignItems(Alignment.CENTER);

        ToggleButtonGroup<String> group5 = new ToggleButtonGroup<>("Your Grade: [read-only] (Hover each grade for more info)",
                List.of("A", "B", "C", "D", "F"));
        group5.setItemTooltipTextGenerator(grade ->
                switch (grade) {
                    case "A" -> "90 - 100";
                    case "B" -> "75 - 89";
                    case "C" -> "65 - 74";
                    case "D" -> "50 - 64";
                    case "F" -> "0 - 49";
                    default -> throw new IllegalStateException("Unexpected value");
                }
        );
        group5.setValue("B");
        group5.setTooltipText("Grades are calculated based on a 0-100 scale system.");
        group5.setReadOnly(true);

        ToggleButtonGroup<String> group6 = new ToggleButtonGroup<>("Disabled group:",
                List.of("All", "Items", "Are", "Disabled", "Selected"));
        group6.setValue("Selected");
        group6.setEnabled(false);

        Map<String, Desert> data = new HashMap<>(Map.of(
                "Jelly", new Desert("Jelly", 100),
                "Ice Cream", new Desert("Ice Cream", 50),
                "Coffee", new Desert("Coffee", 0),
                "Chocolate Cake", new Desert("Chocolate Cake", 10),
                "Quark", new Desert("Quark", 0)));
        ToggleButtonGroup<Desert> group7 = new ToggleButtonGroup<>("Choose desert: [unavailable items are disabled]");
        group7.setItemEnabledProvider(item -> item.availableCount > 0);
        group7.setItems(data.values().stream().toList());
        group7.setItemLabelGenerator(item -> String.format("%s (%d)", item.name, item.availableCount));
        group7.addValueChangeListener(event -> {
            if (event.getOldValue() != null) {
                data.get(event.getOldValue().name).availableCount++;
            }
            if (event.getValue() != null) {
                data.get(event.getValue().name).availableCount--;
            }
            group7.setItems(data.values().stream().toList());
            group7.setValue(event.getValue());
        });

        ToggleButtonGroup<Status> group8 = new ToggleButtonGroup<>();
        group8.setLabel("Status: [custom style for selected item]");
        group8.setItemLabelGenerator(status -> switch (status) {
            case NOT_PROCESSED -> "Not processed";
            case APPROVED -> "Approved";
            case DECLINED -> "Declined";
        });
        group8.setItems(Status.values());
        group8.setSelectedItemClassNameGenerator(status -> "status-" + status.name().toLowerCase());

        ToggleButtonGroup<Status> group9 = new ToggleButtonGroup<>(
                "Status: [change the order of items at runtime]",
                Status.values()
        );
        group9.setItemLabelGenerator(status -> switch (status) {
            case NOT_PROCESSED -> "Not processed";
            case APPROVED -> "Approved";
            case DECLINED -> "Declined";
        });
        group9.setSelectedItemClassNameGenerator(status -> "status-" + status.name().toLowerCase());
        Button customOrderButton = new Button("Custom Order", event ->
            group9.setItemOrderProvider(status -> switch (status) {
                case NOT_PROCESSED -> 1;
                case APPROVED -> 0;
                case DECLINED -> 2;
            }));
        Button originalOrderButton = new Button("Original Order", event -> group9.setItemOrderProvider(null));
        HorizontalLayout line9 = new HorizontalLayout(group9, customOrderButton, originalOrderButton);
        line9.setAlignItems(Alignment.BASELINE);

        ToggleButtonGroup<Status> group10 = new ToggleButtonGroup<>(
                "Status: [orientation = Vertical]",
                Status.values()
        );
        group10.setOrientation(ToggleButtonGroup.Orientation.VERTICAL);

        HorizontalLayout line1 = new HorizontalLayout(group1, lbl1, selected1, clear1);
        line1.setAlignItems(Alignment.BASELINE);
        VerticalLayout halfLayout = new VerticalLayout(line1, group2, group3, line4, group5, group6, group7, group8, line9, group10);
        halfLayout.getStyle().set("width", "50%");
        halfLayout.getStyle().set("border", "solid red 1px");
        add(halfLayout);
    }
}
