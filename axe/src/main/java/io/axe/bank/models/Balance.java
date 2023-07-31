package io.axe.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    private AmountDetails balanceAmount;

    private boolean creditLimitIncluded;
}
