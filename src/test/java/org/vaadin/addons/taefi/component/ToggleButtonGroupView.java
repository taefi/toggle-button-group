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

    enum TextAlignment {
        LEFT, CENTER, RIGHT
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

        ToggleButtonGroup<Menu> group10 = new ToggleButtonGroup<>(List.of(Menu.values()));
        group10.setId("group10");
        group10.setLabel("Menu: [toggleable = false] (ValueChangeListener publishes the selected value)");
        group10.setToggleable(false);
        Label lbl10 = new Label("Selection: ");
        Label selected10 = new Label();
        selected10.setId("group10-selected-value");
        group10.addValueChangeListener(event -> selected10.setText(Optional.ofNullable(event.getValue()).map(Objects::toString).orElse("")));
        Button clear10 = new Button("Clear Selection", event -> group10.setValue(null));
        clear10.setId("clear-group10-selection");
        HorizontalLayout line10 = new HorizontalLayout(group10, lbl10, selected10, clear10);
        line10.setAlignItems(Alignment.BASELINE);

        ToggleButtonGroup<Menu> group15 = new ToggleButtonGroup<>(List.of(Menu.values()));
        group15.setId("group15");
        group15.setLabel("Menu: [toggleable = true (default)] (ValueChangeListener publishes the selected value)");
        Label lbl15 = new Label("Selection: ");
        Label selected15 = new Label();
        selected15.setId("group15-selected-value");
        group15.addValueChangeListener(event -> selected15.setText(Optional.ofNullable(event.getValue()).map(Objects::toString).orElse("")));
        HorizontalLayout line15 = new HorizontalLayout(group15, lbl15, selected15);
        line15.setAlignItems(Alignment.BASELINE);

        ToggleButtonGroup<Status> group20 = new ToggleButtonGroup<>();
        group20.setId("group20");
        group20.setLabel("Status: [width-full] (Hover the mouse to see the tooltip)");
        group20.setTooltipText(String.format("Labels for original values of %s generated based on the configured itemLabelGenerator.",
                Arrays.stream(Status.values()).map(Object::toString).collect(Collectors.joining(", "))));
        group20.setItemLabelGenerator(status -> switch (status) {
            case NOT_PROCESSED -> "Not processed";
            case APPROVED -> "Approved";
            case DECLINED -> "Declined";
        });
        group20.setItems(Status.values());
        group20.setWidthFull();

        ToggleButtonGroup<Direction> group30 = new ToggleButtonGroup<>();
        group30.setId("group30");
        group30.setLabel("Direction: (icons are generated based on itemIconGenerator property)");
        group30.setItemIconGenerator(direction -> switch (direction) {
            case LEFT -> VaadinIcon.ARROW_CIRCLE_LEFT.create();
            case RIGHT -> VaadinIcon.ARROW_CIRCLE_RIGHT.create();
            case UP -> VaadinIcon.ARROW_CIRCLE_UP.create();
            case DOWN -> VaadinIcon.ARROW_CIRCLE_DOWN.create();
            case FORWARD -> VaadinIcon.ARROW_FORWARD.create();
            case BACKWARD -> VaadinIcon.ARROW_BACKWARD.create();
        });
        group30.setItems(Direction.values());

        ToggleButtonGroup<Answer> group40 = new ToggleButtonGroup<>();
        group40.setId("group40");
        group40.setItems(List.of(Answer.values()));
        group40.setValue(Answer.YES);
        HorizontalLayout line40 = new HorizontalLayout(group40, new Span("(No label. Default value is set by calling setValue API)"));
        line40.setAlignItems(Alignment.CENTER);

        ToggleButtonGroup<String> group50 = new ToggleButtonGroup<>("Your Grade: [read-only] (Hover each grade for more info)",
                List.of("A", "B", "C", "D", "F"));
        group50.setId("group50");
        group50.setItemTooltipTextGenerator(grade ->
                switch (grade) {
                    case "A" -> "90 - 100";
                    case "B" -> "75 - 89";
                    case "C" -> "65 - 74";
                    case "D" -> "50 - 64";
                    case "F" -> "0 - 49";
                    default -> throw new IllegalStateException("Unexpected value");
                }
        );
        group50.setTooltipText("Grades are calculated based on a 0-100 scale system.");
        group50.setReadOnly(true);
        Label lbl50 = new Label("Selection: ");
        Label selected50 = new Label();
        selected50.setId("group50-selected-value");
        group50.addValueChangeListener(event -> selected50.setText(Optional.ofNullable(event.getValue()).map(Objects::toString).orElse("")));
        group50.setValue("B");
        HorizontalLayout line50 = new HorizontalLayout(group50, lbl50, selected50);
        line50.setAlignItems(Alignment.BASELINE);

        ToggleButtonGroup<String> group60 = new ToggleButtonGroup<>("Disabled group:",
                List.of("All", "Items", "Are", "Disabled", "Selected"));
        group60.setId("group60");
        group60.setValue("Selected");
        group60.setEnabled(false);

        Map<String, Desert> data = new HashMap<>(Map.of(
                "Jelly", new Desert("Jelly", 100),
                "Ice Cream", new Desert("Ice Cream", 50),
                "Coffee", new Desert("Coffee", 0),
                "Chocolate Cake", new Desert("Chocolate Cake", 10),
                "Quark", new Desert("Quark", 0)));
        ToggleButtonGroup<Desert> group70 = new ToggleButtonGroup<>("Choose desert: [unavailable items are disabled]");
        group70.setId("group70");
        group70.setItemEnabledProvider(item -> item.availableCount > 0);
        group70.setItemOrderProvider(desert -> switch (desert.name) {
            case "Jelly" -> 0;
            case "Ice Cream" -> 1;
            case "Coffee" -> 2;
            case "Chocolate Cake" -> 3;
            case "Quark" -> 4;
            default -> 1000;
        });
        group70.setItems(data.values().stream().toList());
        group70.setItemLabelGenerator(item -> String.format("%s (%d)", item.name, item.availableCount));
        group70.addValueChangeListener(event -> {
            if (event.getOldValue() != null) {
                data.get(event.getOldValue().name).availableCount++;
            }
            if (event.getValue() != null) {
                data.get(event.getValue().name).availableCount--;
            }
            group70.setItems(data.values().stream().toList());
            group70.setValue(event.getValue());
        });

        ToggleButtonGroup<Status> group80 = new ToggleButtonGroup<>();
        group80.setId("group80");
        group80.setLabel("Status: [custom style for selected item]");
        group80.setItemLabelGenerator(status -> switch (status) {
            case NOT_PROCESSED -> "Not processed";
            case APPROVED -> "Approved";
            case DECLINED -> "Declined";
        });
        group80.setItems(Status.values());
        group80.setSelectedItemClassNameGenerator(status -> "status-" + status.name().toLowerCase());
        group80.setValue(Status.APPROVED);

        ToggleButtonGroup<Status> group90 = new ToggleButtonGroup<>(
                "Status: [change the order of items at runtime]",
                Status.values()
        );
        group90.setId("group90");
        group90.setItemLabelGenerator(status -> switch (status) {
            case NOT_PROCESSED -> "Not processed";
            case APPROVED -> "Approved";
            case DECLINED -> "Declined";
        });
        group90.setSelectedItemClassNameGenerator(status -> "status-" + status.name().toLowerCase());
        Button customOrderButton = new Button("Custom Order", event ->
            group90.setItemOrderProvider(status -> switch (status) {
                case NOT_PROCESSED -> 1;
                case APPROVED -> 0;
                case DECLINED -> 2;
            }));
        customOrderButton.setId("custom-order-btn");
        Button originalOrderButton = new Button("Original Order", event -> group90.setItemOrderProvider(null));
        originalOrderButton.setId("original-order-btn");
        HorizontalLayout line90 = new HorizontalLayout(group90, customOrderButton, originalOrderButton);
        line90.setAlignItems(Alignment.BASELINE);

        ToggleButtonGroup<Status> group100 = new ToggleButtonGroup<>(
                "Status: [orientation = Vertical]",
                Status.values()
        );
        group100.setId("group100");
        group100.setOrientation(ToggleButtonGroup.Orientation.VERTICAL);

        ToggleButtonGroup<TextAlignment> group110 = new ToggleButtonGroup<>("Alignment:");
        group110.setItems(TextAlignment.values());
        group110.setItemIconGenerator(align -> switch (align) {
            case LEFT -> VaadinIcon.ALIGN_LEFT.create();
            case CENTER -> VaadinIcon.ALIGN_CENTER.create();
            case RIGHT -> VaadinIcon.ALIGN_RIGHT.create();
        });
        group110.setItemLabelGenerator(textAlignment -> "");

        ToggleButtonGroup<Answer> group120 = new ToggleButtonGroup<>();
        group120.setId("group120");
        group120.setItems(List.of(Answer.values()));
        group120.setValue(Answer.NO);
        // adding the generator last appears to mess with the default selection
        group120.setItemTooltipTextGenerator(answer ->
                switch (answer) {
                    case YES -> "Answer is yes";
                    case NO -> "Answer is no";
                    default -> throw new IllegalStateException("Unexpected value");
                }
        );

        VerticalLayout halfLayout = new VerticalLayout(line10, line15, group20, group30, line40, line50, group60, group70, group80, line90, group100, group110, group120);
        halfLayout.setId("parent-layout");
        halfLayout.getStyle().set("width", "50%");
        halfLayout.getStyle().set("border", "solid red 1px");
        add(halfLayout);
    }
}
