package org.dynjs.runtime.builtins.types.date.prototype;

import org.dynjs.exception.ThrowException;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.GlobalObject;
import org.dynjs.runtime.Types;
import org.dynjs.runtime.builtins.types.date.AbstractDateFunction;
import org.dynjs.runtime.builtins.types.date.DynDate;

public class SetMonth extends AbstractDateFunction {

    public SetMonth(GlobalObject globalObject) {
        super(globalObject, "month", "date");
    }

    @Override
    public Object call(ExecutionContext context, Object self, Object... args) {
        if (!(self instanceof DynDate)) {
            throw new ThrowException(context, context.createTypeError("setMonth(...) may only be used with Dates"));
        }

        DynDate dateObj = (DynDate) self;

        long t = localTime(context, dateObj.getTimeValue());

        Number m = Types.toNumber(context, args[0]);

        Number dt = null;

        if (args[1] != Types.UNDEFINED) {
            dt = Types.toNumber(context, args[1]);
        } else {
            dt = dateFromTime(t);
        }

        Number newDate = makeDate(context, makeDay(context, yearFromTime(t), m, dt), timeWithinDay(t));

        Number u = timeClip(context, utc(context, newDate));

        dateObj.setTimeValue(u);

        return u;

    }
}
