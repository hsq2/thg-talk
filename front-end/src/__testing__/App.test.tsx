import { RenderResult } from "@testing-library/react";
import { act } from "react-dom/test-utils";
import App from "../App"
import { renderWrapper } from "../resources/testing/RenderWrapper"

describe('app', () => {
    it('renders correctly' ,() => {
        let component : RenderResult;
        act(() => {component = renderWrapper(<App/>)})
        const{container} = component!
        expect(container).toMatchSnapshot();
    })
})